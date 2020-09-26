from parse.json_parser import *
from parse.xlsx_parser import *
from test_generation.test_suite import *
from test_prioritization import *


class APFD():
    def __init__(self, test_suite, faults):
        self.test_suite = test_suite
        self.faults = faults
        self.value = 0

    def count(self):
        self.value = 0
        fault_detected = 0
        tc_checked = 0
        tc_order = []
        while tc_checked < len(self.test_suite):
            tc = self.test_suite[tc_checked]
            for fault in self.faults:
                if tc.name in fault.tc_list and not fault.detected:
                    self.value += tc_checked + 1
                    fault.detected = True
                    fault_detected += 1
            tc_order.append(str(tc.name))
            tc_checked += 1
        # print(self.value)
        self.value /= len(self.faults) * len(self.test_suite)
        # print(self.value)
        self.value += 1/(2 * len(self.test_suite))
        # print(self.value)
        self.value = 1 - self.value
        # print(self.value)
        return self.value, tc_order


if (__name__ == "__main__"):
    print("Type the name of the TestSuite file:")
    file = str(input())
    print("Type the name of the suffix:")
    suffix = str(input())
    print("Type the suffix of the Fault:")
    fault_suffix = str(input())
    print("Type the number of iterations:")
    iterations = int(input())

    if suffix == "_PC":
        directory = "pc_test_suite/"
    elif suffix == "_MACO":
        directory = "maco_test_suite/"
    elif suffix == "_Optimal":
        directory = "optimal_test_suite/"
    else:
        directory = "test_suite/"
    if suffix == "_Optimal":
        ts_string = directory + file + "TestSuite" + suffix + "_" + fault_suffix
    else:
        ts_string = directory + file + "TestSuite" + suffix

    for i in range(iterations):
        if suffix == "_MACO" or suffix == "_PC":
            ts = JSONParser().load(ts_string + str(i))
        else:
            ts = JSONParser().load(ts_string)
        # ts = JSONParser().load(ts_string)
        if suffix == "":
            ts_length = int(len(ts.data)/4)
        else:
            ts_length = int(len(ts.data)/2)

        ts.data = ts.data[0:ts_length]
        faults = JSONParser().load("faults/" + file + fault_suffix + "Faults")
        apfd = APFD(ts, faults)
        apfd_value, tc_order = apfd.count()
        print(str(i) + " " + str(apfd_value))
        XLSXParser().write(file, suffix, fault_suffix, apfd_value, tc_order)
