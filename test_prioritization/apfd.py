from parse.json_parser import *


class APFD():
    def __init__(self, test_suite, faults):
        self.test_suite = test_suite
        self.faults = faults
        self.value = 0

    def count(self):
        self.value = 0
        fault_detected = 0
        tc_checked = 0
        while fault_detected < len(self.faults) and tc_checked < len(self.test_suite):
            tc = self.test_suite[tc_checked]
            for fault in self.faults:
                if tc.name in fault.tc_list and not fault.detected:
                    self.value += tc_checked + 1
                    fault.detected = True
                    fault_detected += 1
            print(self.value)
            tc_checked += 1
        self.value /= len(self.faults) * len(self.test_suite)
        self.value += 1/(2 * len(self.test_suite))
        self.value = 1 - self.value
        return self.value


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
    apfd = APFD(ts, faults)
    print(apfd.count())
