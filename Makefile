.PHONY: all build run test

all: build run

build:
	mvn compile

run: build
	mvn exec:java -Dexec.mainClass="de.hso.rechenarchitektur.picsimulator.gui.SimulatorGUI"

test: build
	mvn test
