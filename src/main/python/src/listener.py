import argparse
import pathlib
import pickle
import subprocess
import sys
import time


from utils.server.Client import Client
# Import needed even if some import is not explicitly used
from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics

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

#Starting connection to the server beepbeep
client = Client("localhost", args.port)
beamng = BeamNGpy('localhost', 64256)  # This is the host & port used to communicate over
client.open()
beamng.open()


#Call the scenario script given in args
p = subprocess.Popen([sys.executable, args.scenario], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
stdout, stderr = p.communicate()

#Loads the scenario
scenario = pickle.loads(stdout)

vehicle_from_scenario = list()
for vehicle in scenario.vehicles:
    vehicle_from_scenario.append(vehicle)

# Get the first vehicle, hardcoded but just used for test
vehicle = vehicle_from_scenario[0]

scenario.make(beamng)
beamng.load_scenario(scenario)
beamng.start_scenario()  # After loading, the simulator waits for further input to actually start

vehicle.ai_set_mode('span')

vehicle.update_vehicle()
sensors = beamng.poll_sensors(vehicle)

for _ in range(args.time):
    time.sleep(0.1)
    vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
    sensors = beamng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle
    client.send_packet(sensors)

client.stop_connection()
beamng.close()
input('Hit enter when done...')