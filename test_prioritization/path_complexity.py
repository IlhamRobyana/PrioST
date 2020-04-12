from parse.json_parser import *
from model.alts import *
from test_generation.test_suite import *


class PathComplexity:
    def __init__(self, id):
        self.id = id
        self.complexity = 0
        self.number = 0
        self.weight = 0
        self.predicate = 0
        self.conditions = 0
        self.test_case = TestCase


def prioritze(ts, alts):
    pc_list = []
    for i in range(len(ts.data)):
        pc = PathComplexity(i)
        for j in range(len(ts.data[i].name)+1):
            if j < len(ts.data[i].name):
                state = alts.states[ts.data[i].name[j].from_state.id]
            else:
                state = alts.states[ts.data[i].name[j-1].to_state.id]
            pc.number += 1
            pc.weight += len(state.incoming_transitions) + \
                len(state.outgoing_transitions)
            if len(state.outgoing_transitions) > 1:
                pc.predicate += 1
                pc.conditions += 1
        pc.complexity = pc.number + pc.weight + pc.predicate + pc.conditions
        pc.test_case = ts.data[i]
        pc_list.append(pc)
    pc_list.sort(key=lambda x: x.complexity, reverse=True)
    JSONParser().save(ts.name + "TestSuite_PC", pc_list)


if (__name__ == "__main__"):
    print("Type the name of the ALTS file:")
    file = str(input())
    alts = JSONParser().load(file)

    ts_file = file + "TestSuite"
    ts = JSONParser().load(ts_file)

    prioritze(ts, alts)
