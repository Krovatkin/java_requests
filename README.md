## Why you should ignore this repo.

I put together different ways one can make GET/POST requests pre-java11. This exercise helped to internalize Stream hierarchies in Java.

## I/O

### Reading Streams 

```mermaid
classDiagram
direction TB
	class InputStreamReader {
		a bridge from byte streams to character streams
		+InputStreamReader(InputStream in, Charset cs)
		+InputStreamReader(InputStream in, String charsetName)
		+read(char[] cbuf, int offset, int length)
		+read()
	}
	
	FileInputStream <..  InputStreamReader : created w/
		
	class File {
		+File(String pathname)
	}
	
	class FileInputStream {
		obtains input bytes from a file
		+FileInputStream(File file)
		+read()
		+read(byte[] b)
		+read(byte[] b, int off, int len)
	}

	class ByteArrayInputStream{
		contains bytes that may be read from the stream
		+read(byte[] b, int off, int len)
		+read()
		+mark(int readAheadLimit)
		+reset()
		+skip(long n)
	}
	
	FileInputStream <..> ByteArrayInputStream : dests for Writers
	
	File <..InputStreamReader : created w/
	
	InputStreamReader <|-- FileReader : extends
	class FileReader {
		default character encoding and default buffer size
	}


	InputStreamReader <..  BufferedReader : created w/
	FileReader <..  BufferedReader : created w/
	FileInputStream <..  BufferedInputStream : created w/

	class BufferedReader {
		Reads text from a character-input stream,
		buffering characters
		+read()
		+read(char[] cbuf, int off, int len)
		+readLine()
		+mark(int readAheadLimit)
		+reset()
		+skip(long n) 
	}


	BufferedReader <..> BufferedInputStream : byte vs char

	class BufferedInputStream {
		the ability to buffer the input and 
		to support the mark and reset methods
		+BufferedInputStream(InputStream in, int size)
		+read()
		+read(byte[] b, int off, int len)
		+mark(int readlimit)
		+reset()
		+skip(long n)
	}
```

### Writing Streams

```mermaid
classDiagram
direction TB
	class OutputStreamWriter {
		a bridge from character streams to byte streams
		+OutputStreamWriter(OutputStream out, String charsetName)
		+OutputStreamWriter(OutputStream out, Charset cs)
		+write(char[] cbuf, int off, int len)
		+write(int c)
		+write(String str, int off, int len)
	}
	
	FileOutputStream <..  OutputStreamWriter : created w/
		
	class File {
		+File(String pathname)
	}
	
	class FileOutputStream {
		output stream for writing data 
		to File/FileDescriptor
		+FileOutputStream(File file, boolean append)
	}

	class ByteArrayOutputStream{
		the data is written into a byte array
		+write(byte[] b, int off, int len)
		+write(int b)
		toByteArray()
		+toString(String charsetName)
	}
	
	 FileOutputStream <..> ByteArrayOutputStream : dests for Writers
	
	File <.. FileOutputStream : created w/
	
	OutputStreamWriter <|-- FileWriter : extends
	class FileWriter {
		default character encoding and default buffer size
	}

	File <..  PrintWriter : created w/
	FileOutputStream <..  PrintWriter : created w/
	
	class PrintWriter {
		+ adds printXXX methods
		+PrintWriter(File file, String csn)
		+PrintWriter(OutputStream out)
		+PrintWriter(OutputStream out, boolean autoFlush)
	}

	FileWriter <..  BufferedWriter : created w/
	OutputStreamWriter <..  BufferedWriter : created w/

	class BufferedWriter {
		Writes text to a character-output stream,
		buffering characters
		+BufferedWriter(Writer out) 
	}


	BufferedWriter <..> BufferedOutputStream: byte vs char

	class BufferedOutputStream {
		 a buffered output stream
		 an application can write bytes
		 to the underlying output stream
		+BufferedOutputStream(OutputStream out, int size)
		+write(byte[] b, int off, int len)
		+write(int b)
	}
```