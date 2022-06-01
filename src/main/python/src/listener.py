import argparse
import pathlib
import subprocess
import sys
import time
import logging

from utils.server.Client import Client
# Import needed even if some import is not explicitly used
from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics

logging.basicConfig(level=logging.DEBUG)
parser = argparse.ArgumentParser(description='Listen to a BeamNG scenario given.')

parser.add_argument('--interval', type=int,
                    help='Number of snapshots wanted per seconds (default is 10)')

parser.add_argument('--time', type=int,
                    help='Time of the s')

# Optional, by default is 8080.
parser.add_argument('--port', type=int,
                    help='(default is 8080)')

parser.add_argument('--output', type=pathlib.Path,
                    help='Create a a json file with all snapshots received (optional)')

parser.add_argument('scenario', type=pathlib.Path,
                    help='BeamNG scenario that you want to listen')

args = parser.parse_args()

if args.interval is None:
    args.port = 10

if args.time is None:
    args.time = 240

if args.port is None:
    args.port = 8080

# Starting connection to the server beepbeep
client = Client("localhost", args.port)
client.open()

# Call the scenario script given in args
p = subprocess.run([sys.executable, args.scenario])

# ** Works correctly
beamng = BeamNGpy('localhost', 64256)  # This is the host & port used to communicate over
beamng.connect()
# Test, if it is really connected to the good instance
beamng.display_gui_message("OZOEZE")

logging.debug(beamng.get_scenario_name())
# ** until here

# WHY IS IT EMPTY ?
logging.debug(beamng.get_current_scenario().vehicles)
# It retrieve the vehicle, but doesn't have any captors that was attached through the setup, why ?
vehicle = beamng.get_current_vehicles()['ego']
vehicle.connect(bng=beamng)

# Really terrible for reliability, imagine putting all captors in this script, not his job !
electrics = Electrics()
vehicle.attach_sensor('electrics', electrics)

sensors = beamng.poll_sensors(vehicle)

for _ in range(args.time):
    time.sleep(0.1)
    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = beamng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle
    client.send_packet(sensors)

client.stop_connection()
beamng.close()
input('Hit enter when done...')