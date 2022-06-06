import pickle
import sys

from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics, Damage

# Example of a scenario, you build your scenario and you send it into the buffer
# Note for myself : make less ugly the dumps of the scenario
beamng_instance = BeamNGpy('localhost', 64256)  # This is the host & port used to communicate over
beamng_instance.open()


# Create a vehile instance that will be called 'ego' in the simulation
# using the etk800 model the simulator ships with
vehicle_1 = Vehicle('ego', model='etk800', licence='PYTHON', colour='Green')
vehicle_2 = Vehicle('bruh', model='etk800', licence='EDC', colour='Blue')
# Create an Electrics sensor and attach it to the vehicle
electrics = Electrics()
vehicle_1.attach_sensor('electrics', electrics)
damage = Damage()
vehicle_1.attach_sensor('damage', damage)

electrics = Electrics()
vehicle_2.attach_sensor('electrics', electrics)
damage = Damage()
vehicle_2.attach_sensor('damage', damage)

# Create a scenario called vehicle_state taking p   lace in the west_coast_usa map the simulator ships with
scenario = Scenario('west_coast_usa', 'vehicle_state')
# Add the vehicle and specify that it should start at a certain position and orientation.
# The position & orientation values were obtained by opening the level in the simulator,
# hitting F11 to open the editor and look for a spot to spawn and simply noting down the
# corresponding values.
scenario.add_vehicle(vehicle_1, pos=(-717.121, 101, 118.675), rot=(0, 0, 45))  # 45 degree rotation around the z-axis
scenario.add_vehicle(vehicle_2, pos=(-500, 101, 118.675), rot=(0, 0, -45))  # 45 degree rotation around the z-axis

# Send scenario to listener
# everything in line 30-32 has to be included in every scenario_*****.py
sys.stdout.buffer.write(pickle.dumps(scenario))
scenario.make(beamng_instance)
beamng_instance.load_scenario(scenario)
# If you want to do anything precise with your vehicle,
# it can be only done after the scenario is loaded (after the three lines upside is done)
vehicle_1.ai_set_mode('span')
vehicle_2.ai_set_mode('span')
