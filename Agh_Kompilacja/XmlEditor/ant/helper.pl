#Skrypt uzupelnia wygenrowany kod przez javacc i uzupelnia o brakujace elementy
#malczyk.pawel@gmail.com

print " \n*** Modyfikacja parsera *** \n";

$source_path="src/parser/";
$file_1="ASTAttributeListNode.java";
$file_2="ASTEntityNode.java";
$file_3="ASTDataNode.java";
$file_4="ASTDataAttributeNode.java";
$file_5="XMLDTDParser.java";

modifyASTAttributeListNode();
modifyASTEntityNode();
modifyASTDataNode();
modifyASTDataAttributeNode();
modifyXMLDTDParser();

sub modifyXMLDTDParser() {
	$toReplace = "final public ASTDataAttributeNode parseDataAttribute\(\) throws ParseException";
	$replacement = "final public ASTDataAttributeNode parseDataAttribute() throws Exception";
	$file_name=$source_path.$file_5;
	open(FILE, $file_name);
	@modified=();
	@lines = <FILE>;
	foreach $line (@lines){
	 	$line =~ s/final public ASTDataAttributeNode parseDataAttribute\(\) throws ParseException/ final public ASTDataAttributeNode parseDataAttribute() throws Exception/;
  		push (@modified, $line); 
	}
	close(FILE);


	open(FILE, ">$file_name");
	foreach $ln (@modified) {
		print FILE $ln;
	}
	close(FILE);
}

sub modifyASTAttributeListNode() {
	
	@toInsert = ("public Token attributeNameToken;\n", 
		     "public Token attributeTypeToken;\n",
		     "public Token fixedToken;\n",
		     "public Token attributeValueToken;\n");
		     
	$full_path=$source_path . $file_1;
	modifyAndSave($full_path, @toInsert);
}

sub modifyASTEntityNode() {
	@toInsert = ("public Token valueToken;\n",
		     "public Token name;\n");
	$full_path=$source_path . $file_2;
	modifyAndSave($full_path, @toInsert);
}

sub modifyASTDataNode() {
	@toInsert = ("public Token nameToken;\n");
	$full_path = $source_path . $file_3;
	modifyAndSave($full_path, @toInsert);
}

sub modifyASTDataAttributeNode() {
	@toInsert = ("public Token attributeValueToken;\n");
	$full_path = $source_path . $file_4;
	modifyAndSave($full_path, @toInsert);
}

sub modifyAndSave {
	$full_path = $_[0];
	$toInsert = $_[1];
	
	print "Modyfikuje $full_path\n";
	
	open(FILE, "$full_path");
	@lines=<FILE>;
	
	@modified=();
	
	foreach $line (@lines) {
		$_=$line;
		if(/class/) {
			 push(@modified, $line);
			foreach $ln (@toInsert) {
				 push(@modified, $ln);
			}	
		} else {
			 push(@modified, $line);
		}
	}

	close(FILE);
	
	
	open (FILE, ">$full_path");
	foreach $line (@modified) {
		#print $line;
		print FILE $line;
	}
	close (FILE); 
}

print "\n *** Parser zmodyfikowany *** \n";

