from collections import UserList
from model.alts import *


class TestCase(UserList):

    def __init__(self, name='', transition_list=None):
        super().__init__(initlist=transition_list)
        self.name = name

    def insert(self, transition, position=-1):
        if isinstance(transition, Transition):
            super().insert(position, transition)


class TestSuite(UserList):

    def __init__(self, name='', test_case_list=None):
        super().__init__(test_case_list)
        self.name = name

    def insert(self, test_case, position=-1):
        if isinstance(test_case, TestCase):
            super().insert(position, test_case)


class PCTestCase:
    def __init__(self, name, tc=None):
        self.name = name
        self.complexity = 0
        self.number = 0
        self.weight = 0
        self.predicate = 0
        self.conditions = 0
        self.test_case = tc


class MACOTestCase:
    def __init__(self, name, tc=None):
        self.name = name
        self.complexity = 0
        self.number = 0
        self.weight = 0
        self.predicate = 0
        self.pheromone = 0
        self.test_case = tc


class MACOState:
    def __init__(self, state):
        self.pheromone = 1
        self.heuristic = 2
        self.visited = 0
        self.state = state
        self.dividend = self.pheromone / self.heuristic
        self.child_divisor = (1/2) * len(state.outgoing_transitions)
