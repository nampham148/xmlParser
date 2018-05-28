class companyXmlParser {
    def parser = new XmlParser()

    private static boolean isLeaf(Node node) {
        return (node.children()[0] instanceof String)
    }

    private void helperParseEmployee(Node node, Map data) {
        if (isLeaf(node)) {
            data[node.name()] = node.text()
        } else {
            node.children().each { child ->
                helperParseEmployee(child, data)
            }
        }
    }

    private Map parseEmployee (Node employee) {
        Map data = [:]
        helperParseEmployee(employee, data)
        return data
    }

    void processCompany(String dataPath, Closure action) {
        def company = parser.parse(new File(dataPath))
        List data = []
        company.Employee.each {employee ->
            data << parseEmployee(employee)
        }
        data.each(action)
    }

    static void main(String[] args) {
        String dataPath = args[0]
        def parser = new companyXmlParser()
        parser.processCompany(dataPath) {println it}
    }
}
