.PHONY: all build compile run test clean package help

all: build run

build: compile

compile:
	mvn compile

run:
	mvn exec:java -Dexec.mainClass="de.hso.rechenarchitektur.picsimulator.gui.SimulatorGUI"

test:
	mvn test

test-verbose:
	mvn test -X

clean:
	mvn clean

package:
	mvn package

install:
	mvn install

compile-run: compile run

compile-test: compile test
