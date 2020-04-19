from parse.json_parser import *
from model.alts import *
from test_generation.test_suite import *
from test_prioritization.apfd import *
from test_prioritization.fault import *


class PCTestCase:
    def __init__(self, id):
        self.id = id
        self.complexity = 0
        self.number = 0
        self.weight = 0
        self.predicate = 0
        self.conditions = 0
        self.test_case = TestCase


class PathComplexity:
    def prioritize(self, ts, alts):
        pc_ts = []
        for i in range(int(len(ts.data)/2)):
            pc_tc = PCTestCase(i)
            print(i, end=" : ")
            for j in range(len(ts.data[i].name)+1):
                if j < len(ts.data[i].name):
                    state = alts.states[ts.data[i].name[j].from_state.id]
                    print(state, end=' - > ')
                else:
                    state = alts.states[ts.data[i].name[j-1].to_state.id]
                    print(state)
                pc_tc.number += 1
                pc_tc.weight += len(state.incoming_transitions) * \
                    len(state.outgoing_transitions)
                if len(state.outgoing_transitions) > 1:
                    pc_tc.predicate += 1
                    or_count = state.label.count(" or ")
                    and_count = state.label.count(" and ")
                    pc_tc.conditions += or_count + and_count + 1
            pc_tc.complexity = pc_tc.number + pc_tc.weight + \
                pc_tc.predicate + pc_tc.conditions
            pc_tc.test_case = ts.data[i]
            pc_ts.append(pc_tc)
        pc_ts.sort(key=lambda x: x.complexity, reverse=True)
        return pc_ts


if (__name__ == "__main__"):
    print("Type the name of the ALTS file:")
    file = str(input())
    alts = JSONParser().load(file)

    ts_file = file + "TestSuite"
    ts = JSONParser().load(ts_file)

    pc_ts = PathComplexity().prioritize(ts, alts)
    JSONParser().save(ts.name + "TestSuite_PC", pc_ts)
    for tc in pc_ts:
        print(tc.id, end=" : ")
        print(tc.complexity)

    # faults_file_name = file + "Faults"
    # faults = JSONParser().load(faults_file_name)
    # apfd = APFD(pc_ts, faults)
    # apfd_value = apfd.count()
    # print(apfd_value)
