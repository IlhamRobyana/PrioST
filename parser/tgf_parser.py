import re
from model.alts import *

class TGFParser:

    def __init__(self):
        self.state_re = re.compile('(\w+)\s+(\w+)')
        self.transition_re = re.compile('(\w+)\s+(\w+)\s+([\w| |-]+)')

    def parse(self, tgf_file):
        """
        This method parses a TGF (trivial graph format) into an alts
        :param tgf_file: the path to the tgf file
        :return: an instance of an ALTS
        """
        states = {}
        alts = ALTS()
        with open(tgf_file, mode='r') as tgf:
            line = tgf.readline()
            while line and '#' not in line:
                fields = self.state_re.match(line).groups()
                if len(fields) is 2:
                    states[fields[0]] = State(fields[0], fields[1])
                line = tgf.readline()
            line = tgf.readline()
            while line:
                fields = self.transition_re.match(line).groups()
                if len(fields) is 3:
                    alts.add_transition(Transition(states[fields[0]], '', fields[2], states[fields[1]]))
                line = tgf.readline()
        return alts


if __name__ == '__main__':
    file = 'resources/tgf/ConditionStepResult.tgf'
    parser = TGFParser()
    parser.parse(file)
