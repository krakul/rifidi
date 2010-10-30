#/usr/bin/env python

import manifest
import manifest_parser
import re

if __name__ == '__main__':
	#file = '/tmp/META-INF/MANIFEST.MF'
	f = open('/tmp/META-INF/MANIFEST.MF', 'r')
	file = f.read()
#	print file
	#print re.search(r'\r\n ', file)
	ast = manifest.Ast()
	parser = manifest_parser.ManifestParser()
	parser.parse(file, ast)