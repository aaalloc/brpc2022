import argparse
import json
import os
import pathlib
import importlib
import logging
from concurrent.futures.thread import ThreadPoolExecutor
import time

from utils.server.Client import Client
from beamngpy import BeamNGpy

logging.basicConfig(level=logging.DEBUG)

output_path = None


# Create the output folder, and goes in it.
def create_folder(path):
    global output_path
    output_path = os.path.join(os.getcwd(), path)
    if not os.path.exists(output_path):
        os.mkdir(output_path)


def send_data(car, sock):
    f = None
    for _ in range(args.time):
        time.sleep(0.1)
        car.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
        sensors = beamng_instance.poll_sensors(car)  # Polls the data of all sensors attached to the vehicle
        sock.send_packet(sensors)
        if output_path is not None:
            if os.getcwd() is not output_path:
                logging.debug("goes inside : " + output_path)
                os.chdir(output_path)
            with open("{}.json".format(car.vid), 'a', encoding='utf-8') as f:
                json.dump(sensors, f, ensure_ascii=False)
                f.write("\n")
    sock.close()


def parser_config():
    parser = argparse.ArgumentParser(description='Listen to a BeamNG scenario given.')

    parser.add_argument('--interval', type=int,
                        help='Number of snapshots wanted per seconds (default is 10)')

    parser.add_argument('--time', type=int,
                        help='Time of the s')

    # Optional, by default is 8080.
    parser.add_argument('--port', type=int,
                        help='(default is 8080)')

    parser.add_argument('--output', type=pathlib.Path,
                        help='Create a folder containing all jsons data for all vehicles. (optional)')

    parser.add_argument('scenario', type=pathlib.Path,
                        help='BeamNG scenario that you want to listen')

    args = parser.parse_args()

    if args.interval is None:
        args.port = 10

    if args.time is None:
        args.time = 240

    if args.port is None:
        args.port = 8080

    if args.output is not None:
        create_folder(args.output)

    return args


if __name__ == "__main__":
    args = parser_config()

    # A dict that contains the vehicle, sensors and socket from a vehicle associated to his vid (id basically)
    vehicle_dict = dict()

    scenario_preset = importlib.import_module('utils.scenario.{}'.format(args.scenario))
    scenario_preset.Preset.run()

    # Get instance of current scenario
    beamng_instance = scenario_preset.Preset.get_beamng_instance()
    scenario = scenario_preset.Preset.get_scenario()
    logging.debug(scenario.name)

    # For each vehicle that has been found, initiate a connection to the server
    for vehicle in scenario.vehicles:
        socket = Client("localhost", args.port)
        socket.open()
        vehicle.connect(beamng_instance)
        vehicle_dict[vehicle.vid] = {"vehicle": vehicle, "socket": socket}

    beamng_instance.start_scenario()
    # Sending all vehicles data to server
    with ThreadPoolExecutor() as executor:
        for _, value in vehicle_dict.items():
            executor.submit(send_data, value['vehicle'], value['socket'])

    beamng_instance.close()
