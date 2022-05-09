import time
from server.Client import Client

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics

if __name__ == '__main__':
    client = Client("localhost", 8080)
    client.open()

    beamng = BeamNGpy('localhost', 64256)  # This is the host & port used to communicate over
    beamng.open()

    # Create a vehile instance that will be called 'ego' in the simulation
    # using the etk800 model the simulator ships with
    vehicle = Vehicle('ego', model='etk800', licence='PYTHON', colour='Green')
    # Create an Electrics sensor and attach it to the vehicle
    electrics = Electrics()
    vehicle.attach_sensor('electrics', electrics)

    # Create a scenario called vehicle_state taking place in the west_coast_usa map the simulator ships with
    scenario = Scenario('west_coast_usa', 'vehicle_state')
    # Add the vehicle and specify that it should start at a certain position and orientation.
    # The position & orientation values were obtained by opening the level in the simulator,
    # hitting F11 to open the editor and look for a spot to spawn and simply noting down the
    # corresponding values.
    scenario.add_vehicle(vehicle, pos=(-717.121, 101, 118.675), rot=(0, 0, 45))  # 45 degree rotation around the z-axis

    # The make function of a scneario is used to compile the scenario and produce a scenario file the simulator can load
    scenario.make(beamng)

    beamng.load_scenario(scenario)
    beamng.start_scenario()  # After loading, the simulator waits for further input to actually start

    vehicle.ai_set_mode('span')

    # positions = list()
    # directions = list()
    # wheel_speeds = list()
    # throttles = list()
    # brakes = list()

    vehicle.update_vehicle()
    sensors = beamng.poll_sensors(vehicle)

    for _ in range(240):
        time.sleep(0.1)
        vehicle.update_vehicle()  # Synchs the vehicle's "state" variable with the simulator
        sensors = beamng.poll_sensors(vehicle)  # Polls the data of all sensors attached to the vehicle
        client.send_packet(sensors)
        # positions.append(vehicle.state['pos'])
        # directions.append(vehicle.state['dir'])
        # wheel_speeds.append(sensors['electrics']['wheelspeed'])
        # throttles.append(sensors['electrics']['throttle'])
        # brakes.append(sensors['electrics']['brake'])

    client.stop_connection()
    beamng.close()

    input('Hit enter when done...')
