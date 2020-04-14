import random

from parse.json_parser import *
from model.alts import *
from test_generation.test_suite import *


class MACOTestCase():
    def __init__(self, id, tc=None):
        self.id = id
        self.strength = 0
        self.test_case = tc


class MACOState():
    def __init__(self, state):
        self.pheromone = 1
        self.heuristic = 2
        self.visited = 0
        self.state = state
        self.dividend = self.pheromone / self.heuristic
        self.child_divisor = (1/2) * len(state.outgoing_transitions)


ts = None
alts = None
maco_ts = []
maco_states = {}


class MACO:

    def __probability_all(self, maco_state):
        max_prob = 0
        dest_state = None
        for transition in maco_state.state.outgoing_transitions:
            to_state = maco_states[transition.to_state.id]
            prob = self.__count_probability(maco_state, to_state)
            if max_prob < prob:
                max_prob = prob
                dest_state = to_state
            elif max_prob == prob:
                if dest_state.visited > to_state.visited:
                    dest_state = to_state
                elif dest_state.visited == to_state.visited:
                    if random.randint(0, 2) == 1:
                        dest_state = to_state
        maco_state.child_divisor -= dest_state.dividend
        maco_state.child_divisor += (dest_state.pheromone + 1) / \
            (dest_state.heuristic * 2)
        return dest_state.state

    def __count_probability(self, maco_state, to_state):
        pheromone = to_state.pheromone
        heuristic = to_state.heuristic
        dividend = to_state.dividend
        divisor = maco_state.child_divisor
        prob = dividend/divisor
        return prob

    def __decide_probability(self, max_prob, prob, current_tc, tc, tc_id, id):
        if max_prob < prob:
            max_prob = prob
            current_tc = tc
            tc_id = id
        elif max_prob == prob:
            if random.randint(0, 2) == 1:
                current_tc = tc
                tc_id = id
        return max_prob, current_tc, tc_id

    def __decide_test_case(self, ts, ts_length, decision_transitions):
        current_tc = None
        max_prob = 0
        tc_id = 0
        id = 0
        for j in range(ts_length):
            tc = ts.data[j]
            prob = 0
            dt_count = 0
            if tc is not None:
                for transition in tc.name:
                    if transition in decision_transitions.values():
                        prob += self.__count_probability(
                            maco_states[transition.from_state.id], maco_states[transition.to_state.id])
                        dt_count += 1
                prob /= dt_count
                max_prob, current_tc, tc_id = self.__decide_probability(
                    max_prob, prob, current_tc, tc, tc_id, id)
            id += 1
        ts.data[tc_id] = None
        return tc_id, current_tc

    def prioritize(self, ts, alts):
        decision_transitions = {}
        for state in alts.states.values():
            maco_states[state.id] = MACOState(state)
            if len(state.outgoing_transitions) > 1:
                for transiton in state.outgoing_transitions:
                    decision_transitions[transiton.id] = transiton

        ts_length = int(len(ts.data)/2)
        for i in range(ts_length):
            state = alts.initial_state
            tc_id = 0
            if i == 0:
                tc_id = random.randint(0, ts_length)
                current_tc = ts.data[tc_id]
                maco_tc = MACOTestCase(tc_id, current_tc)
                ts.data[tc_id] = None
            else:
                tc_id, current_tc = self.__decide_test_case(
                    ts, ts_length, decision_transitions)
            maco_tc = MACOTestCase(tc_id, current_tc)
            print(tc_id, end=" : ")
            self.__traverse(maco_tc, current_tc)
        maco_ts.sort(key=lambda x: x.strength, reverse=True)
        JSONParser().save(ts.name + "TestSuite_MACO", maco_ts)
        for tc in maco_ts:
            print(tc.id, end=" : ")
            print(tc.strength)

    def __traverse(self, maco_tc, current_tc):
        for i in range(len(current_tc.name)+1):
            if i < len(current_tc.name):
                state = alts.states[current_tc.name[i].from_state.id]
                print(state, end=' - > ')
            else:
                state = alts.states[current_tc.name[i-1].to_state.id]
                print(state)
            id = state.id

            maco_states[id].visited += 1
            maco_states[id].pheromone += 1/maco_states[id].heuristic
            maco_states[id].heuristic *= 2

            state_weight = len(state.incoming_transitions) + \
                len(state.outgoing_transitions)
            if len(state.outgoing_transitions) > 1:
                state_weight += len(current_tc.name)
            state_weight *= maco_states[id].pheromone

            maco_tc.strength += state_weight

        maco_ts.append(maco_tc)


if (__name__ == "__main__"):
    print("Type the name of the ALTS file:")
    file = str(input())
    alts = JSONParser().load(file)

    ts_file = file + "TestSuite"
    ts = JSONParser().load(ts_file)

    MACO().prioritize(ts, alts)
