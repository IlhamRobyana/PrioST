import xml.sax

from model.activity_diagram import *
from model.alts import *
from parse.json_parser import *


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
            if attributes.get("vertex") == "1":
                value = attributes.get("value")

                vertex = Vertex(id, value, parent, style)
                activityDiagram.add_vertex(vertex)
                if style == "ellipse;whiteSpace=wrap;html=1;aspect=fixed;":
                    activityDiagram.start_node = vertex
            elif attributes.get("edge") == "1":
                source = attributes.get("source")
                target = attributes.get("target")

                edge = Edge(id, parent, style, source, target)
                activityDiagram.add_edge(edge)

    # Call when an elements ends
    def endElement(self, tag):
        self.CurrentData = ""


states = {}
alts = ALTS()


class VertexParser(xml.sax.ContentHandler):
    def _init_(self):
        self.CurrentData = ""

    def startElement(self, tag, attributes):
        self.CurrentData = tag
        if tag == "mxCell":
            id = attributes.get("id")
            parent = attributes.get("parent")
            style = attributes.get("style")
            if attributes.get("vertex") == "1":
                value = attributes.get("value")
                states[id] = State(id, value)

    # Call when an elements ends
    def endElement(self, tag):
        self.CurrentData = ""


class EdgeParser(xml.sax.ContentHandler):
    def __init__(self):
        self.CurrentData = ""

    def startElement(self, tag, attributes):
        self.CurrentData = tag
        if tag == "mxCell":
            id = attributes.get("id")
            parent = attributes.get("parent")
            style = attributes.get("style")
            if attributes.get("edge") == "1":
                source = attributes.get("source")
                target = attributes.get("target")
                alts.add_transition(Transition(
                    states[source], id, '', states[target], 'step'))


if (__name__ == "__main__"):

    # create an XMLReader
    parser = xml.sax.make_parser()
    # turn off namepsaces
    parser.setFeature(xml.sax.handler.feature_namespaces, 0)

    directory = "../resources/xml/"
    print("Type the name of the activity diagram file:")
    file = str(input())

    activityDiagram = ActivityDiagram(file)
    alts.name = file

    # switch to vertexparser
    Handler = VertexParser()
    parser.setContentHandler(Handler)
    parser.parse(directory + file + ".xml")

    # switch to edgeparser
    Handler = EdgeParser()
    parser.setContentHandler(Handler)
    parser.parse(directory + file + ".xml")

    JSONParser().save(alts.name, alts)
