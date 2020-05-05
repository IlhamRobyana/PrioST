from parse.json_parser import *
from model.alts import *
from test_generation.test_suite import *
from test_prioritization.apfd import *
from test_prioritization.fault import *


class Optimal:
    def init_list(self, ts, faults):
        tc_list = []
        for tc in ts.data:
            opt_tc = {}
            opt_tc.update({'name': tc.name})
            opt_tc.update({'test_case': tc.data[0:int(len(tc.data)/2)]})
            fault_list = []
            for i in range(len(faults)):
                if tc.name in faults[i].tc_list:
                    fault_list.append(i+1)
            opt_tc.update({'fault_list': fault_list})
            tc_list.append(opt_tc)
        return tc_list

    def prioritize(self, ts, faults):
        tc_list = self.init_list(ts, faults)
        tc_list = sorted(tc_list, key=lambda i: len(
            i['fault_list']), reverse=True)
        undetected_faults = []

        for i in range(len(faults)):
            undetected_faults.append(i+1)

        for i in range(len(tc_list)):
            print(tc_list[i])
            for fault in tc_list[i]['fault_list']:
                undetected_faults.remove(fault)
                unsorted_list = tc_list[i+1:]
                for tc in unsorted_list:
                    if fault in tc['fault_list']:
                        tc['fault_list'].remove(fault)
                    tc_list.remove(tc)
                sorted_list = sorted(unsorted_list, key=lambda i: len(
                    i['fault_list']), reverse=True)
                tc_list.extend(sorted_list)
        return tc_list


if (__name__ == "__main__"):
    print("Type the name of the TestSuite file:")
    file = str(input())
    ts = JSONParser().load("test_suite/" + file)

    print("Type the name of the Fault file:")
    file = str(input())
    faults = JSONParser().load("faults/" + file)

    ts_length = int(len(ts.data)/4)
    ts.data = ts.data[0:ts_length]
    print(len(ts.data))
    optimal_ts = Optimal().prioritize(ts, faults)
    JSONParser().save("optimal_test_suite/" + ts.name + "TestSuite_Optimal", optimal_ts)
    for tc in optimal_ts:
        print(tc['name'], end=" : ")
        print(tc['fault_list'])
