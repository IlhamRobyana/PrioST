import xml.sax


class ActivityDiagramHandler(xml.sax.ContentHandler):
    """
    CurentData is the type of tag the parser getting
    ID is the id of each vertex/edge
    Style is the xml style for each element, could be used to determine start/end nodes, activity nodes, and decision nodes
    Value is the label for the vertex
    Parent is the parent element for the node, used because labels in edges is considered a vertex, and needed to determine which label belongs to which edge
    Source is the source of an edge
    Target is the target of an edge
    Type is the type of each element, vertex/edge
    """

    def __init__(self):
        self.CurrentData = ""
        self.id = ""
        self.style = ""
        self.value = ""
        self.parent = ""
        self.source = ""
        self.target = ""
        self.type = ""

    # Call when an element starts
    def startElement(self, tag, attributes):
        self.CurrentData = tag
        if tag == "mxCell":
            self.id = attributes.get("id")
            self.parent = attributes.get("parent")
            self.style = attributes.get("style")
            print("ID: ", self.id)
            print("Parent: ", self.parent)
            print("Style: ", self.style)
            if attributes.get("vertex") == "1":
                self.type = "vertex"
                self.value = attributes.get("value")
                print("Type: vertex")
                print("Value: ", self.value)
            elif attributes.get("edge") == "1":
                self.type = "edge"
                self.source = attributes.get("source")
                self.target = attributes.get("target")
                print("Type: edge")
                print("Source: ", self.source)
                print("Target: ", self.target)

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
