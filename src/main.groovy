BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

while (true) {
    println """ Which operation do you want to use?\n0. Add data to database via xml file\n1. Synchronize Online DB with Local DB
2. Quit"""
    def input = br.readLine()
    while (!input.isInteger() || (input.toInteger() < 0 && input.toInteger() > 2)) {
        println "Please enter a valid input"
        input = br.readLine()
    }

    switch (input) {
        case "0":
            def add = new dbAdder()
            println "Enter the file path to use: "
            while (true) {
                input = br.readLine()
                try {
                    add.addXml(input)
                    add.printDB()
                    break
                } catch (FileNotFoundException e) {
                    println "File not found! Enter again"
                }
            }
            break
        case "1":
            def synchronize = new syncOnlDB()
            synchronize.sync()
            println "Synchronized successfully!"
            break
        case "2":
            return
    }
}