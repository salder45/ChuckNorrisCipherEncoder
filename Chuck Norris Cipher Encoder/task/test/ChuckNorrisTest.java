import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChuckNorrisTest extends StageTest {
  class Case{
    String input;
    String result;
    String invert;
    Case(String input, String result, String invert){
      this.input=input;
      this.result=result;
      this.invert=invert;
    }
  }
  Object[] test_data(){
    String ascii = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    List<String> list = new ArrayList<>(Arrays.asList(ascii.split("")));
    list.addAll(Arrays.asList("greetings!",
            "hello world!",
            ascii));
    List<Case> r = new ArrayList<>();
    for(String s : list){
      String code = "";
      String result_inv = "";
      for (int i=0;i<s.length();i++){
        String result = Integer.toBinaryString(s.charAt(i));
        String resultWithPadding = String.format("%7s", result).replaceAll(" ","0");
        code = code.concat(resultWithPadding);
      }
      for(int i=0;i<code.length()/7;i++){
        String tmp = code.substring(i*7,(i+1)*7).replace('0', '2').replace('1', '0').replace('2', '1');
        char c =  (char)Integer.parseInt(tmp,2);
        result_inv = result_inv.concat(String.valueOf(c));
      }
      String result="";
      char x=code.charAt(0);
      int start=0;
      for(int i=1;i<code.length();i++){
        if(code.charAt(i)!=x){
          result = result.concat(x=='1'?"0 ":"00 ").concat("0".repeat(i-start)+" ");
          x=code.charAt(i);
          start=i;
        }
      }
      result = result.concat(x=='1'?"0 ":"00 ").concat("0".repeat(code.length()-start)+" ");

      r.add(new Case(result,s,result_inv));
    }
    return r.toArray();
  }

  @DynamicTest(data = "test_data")
  CheckResult test(Case input_case) {
    TestedProgram pr = new TestedProgram();
    String output = pr.start().strip().toLowerCase();
    List<String> list = new ArrayList<>(Arrays.asList(output.split("\n")));
    list.removeAll(Arrays.asList(""));

    if(list.size()!=1 || !list.get(0).contains("input")){
      return CheckResult.wrong("When the program just started, output should contain exactly 1 non-empty line, " +
              "containing \"input\" substring as it shown in the example, followed by an input");
    }
    output = pr.execute(input_case.input);
    list = new ArrayList<>(Arrays.asList(output.split("\n")));
    list.removeAll(Arrays.asList(""));
    if(list.size()!=2){
      return CheckResult.wrong("When the user has entered a string, there should be printed exactly 2 " +
              "non-empty lines");
    }
    if(!list.get(0).toLowerCase().contains("result")){
      return CheckResult.wrong("When the user has entered a string, the first line of the output " +
              "should contain \"result\" substring");
    }
    if(list.get(1).equals(input_case.invert)){
      return CheckResult.wrong("Input string was not decoded correctly, in this case the reason might be that you've " +
              "decoded '0' sequence with first block \"0\" and '1' sequence with first block \"00\", so the decoded " +
              "string is \"inverted\"");
    }
    if(!list.get(1).equals(input_case.result)){
      return CheckResult.wrong("Input string was not decoded correctly.");
    }

    return CheckResult.correct();
  }
}