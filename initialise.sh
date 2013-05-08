#!/bin/bash

#############
#
# Script to resolve the plugin's lib directory dependencies
# and generate the SQLParser sources using javacc
#
# Note. This has to build both the target platform and the
#       spi plugin in order to satisfy the dependencies of
#       this plugin (even though we are not actually 
#       compiling it!)
#
#############

PLUGIN_HOME=$PWD
GIT_HOME=`cd "../../.."; pwd`
SCRIPTS_HOME="$GIT_HOME/scripts"
MVN="$SCRIPTS_HOME/maven-wrapper.sh"

LIBS=(			connector-api.jar \
			jboss-as-cli.jar \
			jboss-as-controller-client.jar \
			jboss-as-controller.jar \
			jboss-as-protocol.jar \
			jboss-dmr.jar \
			jboss-logging.jar \
			jboss-marshalling-river.jar \
			jboss-marshalling.jar \
			jboss-modules.jar \
			jboss-remoting.jar \
			jboss-sasl.jar \
			jboss-threads.jar \
			jboss-vfs.jar \
			jgroups.jar \
			json-simple.jar \
			jta.jar \
			nux.jar \
			rhq-pluginAnnotations.jar \
			xnio-api.jar \
			xnio-nio.jar \
			xom.jar)

SQLPARSER="engine/src/main/javacc/org/teiid/query/parser/SQLParser.java"

PROCESS="no"

# Check to see if all the libraries have been downloaded
for i in ${!LIBS[*]}
do
  LIB=${LIBS[$i]}

	if [ ! -f lib/$LIB ]; then
		PROCESS="yes"
		break
	fi
done

# Check to see that the SQLParser has been generated
if [ ! -f $SQLPARSER ]; then
	PROCESS="yes"
fi

if [ "x$PROCESS" == "xno" ]; then
	echo "Project source generation is up-to-date"
	exit 0
fi

# Build the target platform and install it
echo "=== Installing target platform ==="
cd "$GIT_HOME/target-platform"
$MVN install

# Build the spi dependency and install it
echo "=== Installing the spi plugin dependency ==="
cd "$GIT_HOME/plugins/org.teiid.designer.spi"
$MVN install

echo "=== Generating external libraries and sources ==="
cd "$PLUGIN_HOME"
$MVN process-sources -Pgenerate-javacc
