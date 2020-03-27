import xml.sax
import jsonpickle

from alts import *


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


class ActivityDiagramHandler(xml.sax.ContentHandler):
    """
    CurentData is the type of tag the parser getting
    ActivityDiagram is the activity diagram being parsed
    """

    def __init__(self):
        self.CurrentData = ""

    # Call when an element starts
    def startElement(self, tag, attributes):
        self.CurrentData = tag
        if tag == "mxCell":
            id = attributes.get("id")
            parent = attributes.get("parent")
            style = attributes.get("style")

            # print("ID: ", id)
            # print("Parent: ", parent)
            # print("Style: ", style)
            if attributes.get("vertex") == "1":
                value = attributes.get("value")

                vertex = Vertex(id, value, parent, style)
                activityDiagram.add_vertex(vertex)
                if style == "ellipse;whiteSpace=wrap;html=1;aspect=fixed;":
                    activityDiagram.start_node = vertex
                # print("Type: vertex")
                # print("Value: ", value)
            elif attributes.get("edge") == "1":
                source = attributes.get("source")
                target = attributes.get("target")

                edge = Edge(id, parent, style, source, target)
                activityDiagram.add_edge(edge)
                # print("Type: edge")
                # print("Source: ", source)
                # print("Target: ", target)

    # Call when an elements ends
    def endElement(self, tag):
        self.CurrentData = ""


if (__name__ == "__main__"):

    # create an XMLReader
    parser = xml.sax.make_parser()
    # turn off namepsaces
    parser.setFeature(xml.sax.handler.feature_namespaces, 0)

    # override the default ContextHandler
    Handler = ActivityDiagramHandler()
    parser.setContentHandler(Handler)

    parser.parse("../resources/xml/BubbleSortActivityDiagram.xml")
    activityDiagram.convert_to_alts()
