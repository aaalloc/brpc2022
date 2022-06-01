import sys

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics
import pickle

# Example of a scenario, you build your scenario and you send it into the buffer
# Note for myself : make less ugly the dumps of the scenario

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

# Send scenario to listener
sys.stdout.buffer.write(pickle.dumps(scenario))
