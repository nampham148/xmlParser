def plan = new XmlParser().parse(new File('plan.xml')) 

println plan.name()
println plan.week.length
