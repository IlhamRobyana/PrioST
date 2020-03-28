class ALTS:
    def __init__(self, name=''):
        """
        ALTS stands for Annotated Labeled Transition System. It is defined as a 4-tuple (S, A, L, T, s_i), where:
            * S is a set of states (i.e. vertexes in a graph)
            * A is a set of actions
            * L is a set of labels (applied for all s in S and all a in A)
            * T is the transition relation S x A X S (i.e. edges in a graph)
            * s_i is the initial state (i.e. the root of a tree structure)
        :param name: a name for the alts
        """
        self.name = name
        self.initial_state = None
        self.states = {}  # key:the label of the state | value: the instance itself
        # key: tuple formed by the fields of the transition | value: the instance itself
        self.transitions = {}

    def __contains__(self, item):
        """
        Tests whether a transition is already in the ALTS. Just test with the operator 'is'
        :param item: the other transition to be checked
        :return: a boolean value indicating whether the alts already contains the transition
        """
        return isinstance(item, Transition) and repr(item) in self.transitions.keys()

    def add_transition(self, transition_to_add):
        """
        Adds a new transition to the ALTS
        :param transition_to_add: the new transition to be added.
        """
        if isinstance(transition_to_add, Transition) and transition_to_add not in self:
            self.transitions[repr(transition_to_add)] = transition_to_add
            if not self.initial_state:
                self.initial_state = transition_to_add.from_state
            self.__add_node(transition_to_add.from_state)
            self.__add_node(transition_to_add.to_state)

    def __add_node(self, state_to_add):
        """
        Private method to add a single state into the ALTS.
        :param state_to_add: the state to be added
        """
        if isinstance(state_to_add, State) and repr(state_to_add) not in self.states:
            self.states[state_to_add.id] = state_to_add


class State:
    def __init__(self, id, label):
        """
        State is a component of an ALTS, which in practice behaves as a node in a graph
        :param label: the label of a state
        """
        self.label = label
        self.id = id
        self.incoming_transitions = []
        self.outgoing_transitions = []

    def is_leaf(self):
        return len(self.outgoing_transitions) == 0

    def __repr__(self):
        return self.label

    def __eq__(self, other):
        return isinstance(other, State) and self.label == other.label and self.id == other.id


class Transition:
    def __init__(self, from_state, id, label, to_state, transition_type=''):
        """
        Transition is a component of an ALTS, which in practice behaves as an edge in a graph, specifically in a digraph
        :param transition_type: a type to be associated to the transition
        :param from_state: the state that this transition comes from
        :param label: the label of a transition
        :param to_state: the state that this transition leads to
        """
        if not isinstance(from_state, State):
            raise TypeError(
                'The state \"from\" this transition is not instance of State')

        if not isinstance(to_state, State):
            raise TypeError(
                'The state \"to\" this transition is not instance of State')

        self.transition_type = transition_type
        self.from_state = from_state
        self.id = id
        self.label = label
        self.to_state = to_state
        self.from_state.outgoing_transitions.append(self)
        self.to_state.incoming_transitions.append(self)

    def __repr__(self):
        prefix = '{} -> '.format(self.from_state)
        middle = '({}) '.format(
            self.transition_type) if self.transition_type else ''
        middle += self.label
        suffix = ' -> {}'.format(self.to_state)
        return prefix + middle + suffix

    def __eq__(self, other_transition):
        return isinstance(other_transition, Transition) and \
            self.from_state == other_transition.from_state and \
            self.id == other_transition.id and \
            self.label == other_transition.label and \
            self.to_state == other_transition.to_state and \
            self.transition_type == other_transition.transition_type
