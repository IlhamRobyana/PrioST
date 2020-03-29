import jsonpickle


class JSONParser:
    def save(self, name, item):
        f = open('../resources/json/' + name + '.json', 'w')
        json = jsonpickle.encode(item)
        f.write(json)

    def load(self, name):
        g = open('../resources/json/' + name + '.json')
        json_str = g.read()
        item = jsonpickle.decode(json_str)
        return item
