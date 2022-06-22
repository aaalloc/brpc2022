from beamngpy import BeamNGpy, Vehicle, Scenario
from beamngpy.sensors import Electrics, Damage


class ConfigScenario:
    scenario = None
    beamng_instance = None

    def __init__(self):
        pass

    @classmethod
    def get_scenario(cls):
        return cls.scenario

    @classmethod
    def get_beamng_instance(cls):
        return cls.beamng_instance

    # Example of what a "scenario" should look like.

    # Take not of the cls.beamng_instance and cls.scenario,
    # you absolutely need to invoke that cls key word, or the global var wont change !
    @classmethod
    def run(cls):
        cls.beamng_instance = BeamNGpy('localhost', 64256)  # This is the host & port used to communicate over
        cls.beamng_instance.open()

        # Create a vehile instance that will be called 'ego' in the simulation
        # using the etk800 model the simulator ships with
        vehicle_1 = Vehicle('ego', model='etk800', licence='PYTHON', colour='Green')
        vehicle_2 = Vehicle('bruh', model='etk800', licence='EDC', colour='Blue')
        # Create an Electrics sensor and attach it to the vehicle
        electrics = Electrics()
        vehicle_1.attach_sensor('electrics', electrics)
        damage = Damage()
        vehicle_1.attach_sensor('damage', damage)

        # Create a scenario called vehicle_state taking p   lace in the west_coast_usa map the simulator ships with
        cls.scenario = Scenario('west_coast_usa', 'vehicle_state')
        # Add the vehicle and specify that it should start at a certain position and orientation.
        # The position & orientation values were obtained by opening the level in the simulator,
        # hitting F11 to open the editor and look for a spot to spawn and simply noting down the
        # corresponding values.
        cls.scenario.add_vehicle(vehicle_1, pos=(-717.121, 101, 118.675),
                                 rot=(0, 0, 45))  # 45 degree rotation around the z-axis

        # Really important !
        cls.scenario.make(cls.beamng_instance)
        cls.beamng_instance.load_scenario(cls.scenario)

        # enable auto driving by an AI on vehicle_1
        vehicle_1.ai_set_mode('span')
