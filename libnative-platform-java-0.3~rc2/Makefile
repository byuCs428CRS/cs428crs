IDIR=src/main/headers
SRCDIR=src/main/cpp
JSRCDIR=src/main/java
BUILD=build
IGEN=$(BUILD)/headers
JCLASSESDIR=$(BUILD)/classes
JDOCDIR=$(BUILD)/docs/javadoc
ODIR=$(BUILD)/binaries
JAR=$(BUILD)/native-platform.jar

JTARGET=1.5
JSOURCE=1.5

INCLUDE=-I$(IDIR) -I$(IGEN) -I/usr/include -I/usr/lib/jvm/default-java/include
#LDLIBS=-lncurses

_OBJ_STD = generic.o generic_posix.o linux.o osx.o posix.o win.o
_OBJ_CURSES = generic.o generic_posix.o curses.o
OBJ_STD = $(patsubst %,$(ODIR)/%,$(_OBJ_STD))
OBJ_CURSES = $(patsubst %,$(ODIR)/%,$(_OBJ_CURSES))
_JAVA = $(shell find $(JSRCDIR) -name "*.java" -print)

.PHONY: clean jar javadoc

build: $(ODIR)/libnative-platform.so $(ODIR)/libnative-platform-curses.so jar javadoc

$(ODIR)/libnative-platform.so: $(OBJ_STD)
	$(CXX) $(CFLAGS) $(CXXFLAGS) $(CPPFLAGS) $(LDFLAGS) -shared -o $@ $^

$(ODIR)/libnative-platform-curses.so: $(OBJ_CURSES)
	$(CXX) $(CFLAGS) $(CXXFLAGS) $(CPPFLAGS) $(LDFLAGS) -shared -o $@ $^ -lcurses

$(JCLASSESDIR)/%.class: javafiles-list
	mkdir -p $(JCLASSESDIR)
	$(JAVA_HOME)/bin/javac -source $(JTARGET) -target $(JSOURCE) -d $(JCLASSESDIR) -classpath /usr/share/java/joptsimple.jar @javafiles-list

javafiles-list: $(_JAVA)
	echo $^ > javafiles-list

javadoc: javafiles-list
	mkdir -p $(JDOCDIR)
	$(JAVA_HOME)/bin/javadoc -d $(JDOCDIR) -classpath /usr/share/java/joptsimple.jar @javafiles-list

jar: $(JCLASSESDIR)/%.class
	mkdir -p $(JDOCDIR)
	(cd $(JCLASSESDIR) ; $(JAVA_HOME)/bin/jar cf ../../$(JAR) .)

$(ODIR)/%.o: $(SRCDIR)/%.cpp $(IGEN)/native.h
	mkdir -p $(ODIR)
	$(CXX) $(CFLAGS) $(CXXFLAGS) $(CPPFLAGS) $(LDFLAGS) $(INCLUDE) -fPIC -c -o $@ $<

$(IGEN)/native.h: $(JCLASSESDIR)/%.class
	mkdir -p $(IGEN)
	$(JAVA_HOME)/bin/javah -o $(IGEN)/native.h -classpath $(JCLASSESDIR) \
	net.rubygrapefruit.platform.internal.jni.NativeLibraryFunctions \
	net.rubygrapefruit.platform.internal.jni.PosixFileFunctions \
	net.rubygrapefruit.platform.internal.jni.PosixFileSystemFunctions \
	net.rubygrapefruit.platform.internal.jni.PosixProcessFunctions \
	net.rubygrapefruit.platform.internal.jni.PosixTerminalFunctions \
	net.rubygrapefruit.platform.internal.jni.TerminfoFunctions \
	net.rubygrapefruit.platform.internal.jni.WindowsConsoleFunctions \
	net.rubygrapefruit.platform.internal.jni.WindowsHandleFunctions

clean:
	-rm javafiles-list
	-rm -Rf $(BUILD)
