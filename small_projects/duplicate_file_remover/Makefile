compile:
	mkdir -p classes
	javac -d classes *.java

run: compile
	java -cp classes com.example.DuplicateFileDeleter ~/Pictures

clean:
	rm -rf classes