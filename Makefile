all: clean class jar

class:
	@echo "$@"
	javac -Xlint:unchecked ru/cyberbiology/test/MainWindow.java

jar: ru/cyberbiology/test/*.class
	@echo "$@"
	@jar -cvmf META-INF/* world.jar $$(find ./ru/cyberbiology/ -type f -name "*.class" | xargs echo) 
	mkdir -p ./build/
	mv world.jar ./build/

clean:
	@echo "$@"
	find . -type f -name *.class -exec rm -rf {} \;
	rm -rf ./build
