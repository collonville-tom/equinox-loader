docker pull collonvtom/tc-maven:latest
docker run -it --rm -v repomaven:/repolocal -v $(cygpath -w $(pwd)):/maven_work_dir --name mvn collonvtom/tc-maven:latest
