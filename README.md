# word-count

## Instructions

Given an arbitrary text document written in English, write a program that will generate a  concordance, i.e. an alphabetical list of all word occurrences, labeled with word  frequencies.

Bonus: label each word with the sentence numbers in which each occurrence appeared.  

### Example

The text above would output the following

| word | {count: list of pages} |
|--- | --- |
| a | {2:1,1} |
| all | {1:1} | 
| alphabetical | {1:1} |  
| an | {2:1,1} |  
| appeared | {1:2} |
| arbitrary | {1:1} | 
| bonus | {1:2} | 
| concordance | {1:1} | 
| document | {1:1} | 
| each | {1:2} | 
| english | {1:1} | 
| frequencies | {1:1} | 
| generate | {1:1} | 
| given | {1:1} | 
| i.e. | {1:1} | 
| in | {2:1,2} | 
| it | {1:2} | 
| label | {1:2} | 
| labeled | {1:1} | 
| list | {1:1} | 
| numbers | {1:2} | 
| occurrences | {1:1} | 
| of | {1:1} | 
| program | {1:1} | 
| sentence | {1:2} | 
| text | {1:1} | 
| that | {1:1} | 
| the | {1:2} | 
| which | {1:2} | 
| will | {1:1} | 
| with | {2:1,2} | 
| word | {3:1,1,2} | 
| write | {1:1} | 
| written | {1:1} |

## Running

To run this, as long as maven and Java 11 are installed, you should just be able to run the below command after cloning the repo.
```sh
mvn spring-boot:run
```

You should also just be able to pull it into Intellij or Eclipse using the pom.xml file and everything should just work there as well assuming you have the spring-boot plugins.

If you want to test a different set of text, just save it to a `.txt` file and save that file in `src/main/resources`, then when prompted for the file name, enter that one instead of `example1.txt`.
