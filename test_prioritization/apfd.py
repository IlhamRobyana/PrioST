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
        self.value /= len(self.faults) * len(self.test_suite)
        self.value += 1/(2 * len(self.test_suite))
        self.value = 1 - self.value
        return self.value, tc_order


if (__name__ == "__main__"):
    print("Type the name of the TestSuite file:")
    file = str(input())
    print("Type the name of the suffix:")
    suffix = str(input())

    if suffix == "_PC":
        directory = "pc_test_suite/"
    elif suffix == "_MACO":
        directory = "maco_test_suite/"
    elif suffix == "_Optimal":
        directory = "optimal_test_suite/"
    else:
        directory = "test_suite/"
    ts = JSONParser().load(directory + file + "TestSuite" + suffix)

    print("Type the name of the Fault:")
    fault_suffix = str(input())
    faults = JSONParser().load("faults/" + file + fault_suffix + "Faults")

    if isinstance(ts.data[0], TestCase):
        ts_length = int(len(ts.data)/4)
    else:
        ts_length = int(len(ts.data)/2)
    ts.data = ts.data[0:ts_length]
    apfd = APFD(ts, faults)
    apfd_value, tc_order = apfd.count()
    XLSXParser().write(file, suffix, fault_suffix, apfd_value, tc_order)
