from parse.json_parser import *
from model.alts import *
from test_generation.test_suite import *
from test_prioritization.apfd import *
from test_prioritization.fault import *


class PathComplexity:
    def prioritize(self, ts, alts):
        pc_ts = []
        for i in range(int(len(ts.data)/4)):
            pc_tc = PCTestCase(i+1)
            print(i + 1, end=" : ")
            for j in range(int(len(ts.data[i].data)/2)+1):
                if j < len(ts.data[i].data)/2:
                    state = alts.states[ts.data[i].data[j].from_state.id]
                    print(state, end=' - > ')
                else:
                    state = alts.states[ts.data[i].data[j-1].to_state.id]
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
    print("Type the name of the Graph file:")
    file = str(input())
    alts = JSONParser().load("alts/" + file)

    ts_file = file + "TestSuite"
    ts = JSONParser().load("test_suite/"+ts_file)

    pc_ts = PathComplexity().prioritize(ts, alts)
    pc_ts = TestSuite(ts.name, pc_ts)
    JSONParser().save("pc_test_suite/" + ts.name + "TestSuite_PC", pc_ts)
    for tc in pc_ts:
        print(tc.name, end=" : ")
        print(tc.complexity, end=", ")
        print(tc.number, end=", ")
        print(tc.weight, end=", ")
        print(tc.predicate, end=", ")
        print(tc.conditions)
