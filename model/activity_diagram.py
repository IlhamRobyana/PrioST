import jsonpickle
from model.alts import *

class ActivityDiagram:
    """
    Name is the name of the activity diagram
    start_node is the beginning node of the activity diagram
    """

    def __init__(self, name):
        self.name = name
        self.start_node = None
        self.vertices = {}  # key:ID of the vertex
        self.edges = {}  # key:ID of the edge

    def add_vertex(self, vertex_to_add):
        if isinstance(vertex_to_add, Vertex) and repr(vertex_to_add) not in self.vertices:
            self.vertices[repr(vertex_to_add)] = vertex_to_add

    def add_edge(self, edge_to_add):
        if isinstance(edge_to_add, Edge) and edge_to_add not in self.edges:
            self.edges[edge_to_add.id] = edge_to_add

    def convert_to_alts(self):
        alts = ALTS('test')
        for edge in self.edges.values():
            source = State(edge.source, self.vertices[edge.source].value)
            target = State(edge.target, self.vertices[edge.target].value)
            transition = Transition(
                source, edge.id, edge.value, target, 'step')
            alts.add_transition(transition)

        # TODO: Make them their own modules
        f = open('../resources/json/' + alts.name + '.json', 'w')
        json = jsonpickle.encode(alts)
        f.write(json)

        g = open('../resources/json/' + alts.name + '.json')
        json_str = g.read()
        obj = jsonpickle.decode(json_str)
        print(obj.transitions)


activityDiagram = ActivityDiagram("test")


class Vertex:
    def __init__(self, id, value, parent, style):
        """
        ID is the id of each vertex
        Style is the xml style for each element, could be used to determine start/end nodes, activity nodes, and decision nodes
        Value is the label for the vertex
        Parent is the parent element for the node, used because labels in edges is considered a vertex, and needed to determine which label belongs to which edge
        """
        self.id = id
        self.value = value
        self.parent = parent
        self.style = style
        self.incoming_edges = []
        self.outgoing_edges = []

    def __repr__(self):
        return self.id


class Edge:
    def __init__(self, id, parent, style, source, target):
        """
        ID is the id of each edge
        Style is the xml style for each element, could be used to determine start/end nodes, activity nodes, and decision nodes
        Value is the value of the edge
        Parent is the parent element for the node, used because labels in edges is considered a vertex, and needed to determine which label belongs to which edge
        Source is the source of an edge
        Target is the target of an edge
        """
        self.id = id
        self.parent = parent
        self.style = style
        self.value = ''
        self.source = source
        self.target = target
