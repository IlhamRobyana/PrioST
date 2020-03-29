from test_generation.test_suite import TestSuite, TestCase
from model.alts import *
from parse.json_parser import *


class TestCaseGenerator:

    def all_n_loop(self, alts, n=0):
        """
        Runs a simple DFS on the alts and stops the traversing when the current state is a leaf (no outgoing transition)
        or when the last transition traverses at most n times a already traversed state. By default, n is equals to 0
        and the algorithm does not traverse loop, i.e. if the transition leads to a already traversed state, it stops
        generating the partial path
        :param alts: the input alts to generate test cases from
        :param n: the number of times a loop needs to be traversed
        :return: an instance of the TestSuite
        """
        generated_test_suite = TestSuite()
        self.__traverse(alts.initial_state, [], n, generated_test_suite)
        return generated_test_suite

    def __traverse(self, state, current_path, n, test_suite):
        if state.is_leaf() or self.__occurrences(current_path, state) > n:
            tc = TestCase(current_path)
            test_suite.insert(tc)

        for neighbor_transition in state.outgoing_transitions:
            next_path = current_path.copy()
            next_path.append(neighbor_transition)
            self.__traverse(neighbor_transition.to_state,
                            next_path, n, test_suite)

    @staticmethod
    def __occurrences(path, state):
        occurrences = 0
        if len(path) > 0:
            for t in path:
                if path[0].from_state == state:
                    occurrences += 1

        return occurrences


if __name__ == '__main__':
    print("Type the name of the ALTS file:")
    file = str(input())
    alts = JSONParser().load(file)

    ts = TestCaseGenerator().all_n_loop(alts)
    ts.name = file
    JSONParser().save(file + "TestSuite", ts)
