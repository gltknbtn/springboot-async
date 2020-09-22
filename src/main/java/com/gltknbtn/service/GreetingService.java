package com.gltknbtn.service;

import com.gltknbtn.SamplebootappApplication;
import com.gltknbtn.annotation.LogMethodPerformance;
import com.gltknbtn.annotation.Person2;
import com.gltknbtn.data.GreetingResponse;
import com.gltknbtn.model.Person;
import com.gltknbtn.repository.PersonRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GreetingService {

    Logger logger = Logger.getLogger(GreetingService.class);
    @Autowired private PersonRepository personRepository;
    @Autowired private JsonSerializeService jsonSerializeService;

    // not thread safe
    private static String suffix;

    private String name = "";

    @Autowired AsyncServic asyncService;

    @LogMethodPerformance(name = "method1Name")
    public GreetingResponse method1(String input) throws Exception {

        GreetingResponse response = new GreetingResponse();

      try{
          name = input;

          String data = input +" - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

          setSuffix(input);

          data = data + suffix;

          response.setData(data);

          Person2 person2 = new Person2();
          person2.setFirstName("gültekiN");
          person2.setLastName("bütüN");
          person2.setAddress("Adres1 city1");
          person2.setAge("23");

          String responseJson = jsonSerializeService.getObjAsJsonString(person2);
          System.out.println("responseJson " + responseJson);
          logger.info("responseJson " + responseJson);
          String str = "abcDee432fdafda";
          System.out.println(str.replaceAll("^abcDee", "AAA"));

          testForRegex();

          if(true) throw new RuntimeException("runtime exception dir");
      }catch(Exception e){
          throw e;
      }

        return response;
    }

    private void testForRegex() {
        String alphanumeric = "abcCDeAeFjdklm123456789";
        System.out.println(alphanumeric.replaceAll("^abc", "X")); // ^abc: abc ile baslayan string
        System.out.println(alphanumeric.replaceAll("jklm$", "X"));// abc$: abc ile biten string

        System.out.println(alphanumeric.replaceAll("[ajm]", "X"));// a veya j veya m yi X ile değiştir
        System.out.println(alphanumeric.replaceAll("[aj][bd]", "X"));// ab, ad, jb, jd geçen ifadeleri X ile değiştir
        System.out.println("gültekin".replaceAll("[Gg]ültekin", "Gültekin"));// Gültekin veya gültekin, "Gültekin" ile değiştirilir

        System.out.println(alphanumeric.replaceAll("[^ej]", "X")); // e veya j hariç diğer harfleri X ile değiştir.

        System.out.println(alphanumeric.replaceAll("[abc123]", "X")); // a,b,c,1,2,3 harflerini X ile değiştirir.
        System.out.println(alphanumeric.replaceAll("[a-c1-3]", "X")); // a ile c aralığındaki harfleri ve ,1 ile 3 aralığındaki harflerini X ile değiştirir.

        System.out.println(alphanumeric.replaceAll("[a-cA-C1-3]", "X")); // a ile c aralığındaki harfleri(büyük harfler dahil) ve ,1 ile 3 aralığındaki harflerini X ile değiştirir.
        System.out.println(alphanumeric.replaceAll("(?i)[a-c1-3]", "X")); //(?i):ignore case demek.. a ile c aralığındaki harfleri(büyük harfler dahil) ve ,1 ile 3 aralığındaki harflerini X ile değiştirir.

        System.out.println(alphanumeric.replaceAll("[0-9]", "X")); // 0 ile 9 aralığındaki tüm sayıları X ile değiştir.
        System.out.println(alphanumeric.replaceAll("\\d", "X")); // digit olanları(sayı) X ile değiştir.
        System.out.println(alphanumeric.replaceAll("\\D", "X")); // digit olmayanları (sayı olmayanları) X ile değiştir.

        String hasWhiteSpace = "I have blanks and\t a tab and also a new line\n";
        System.out.println(hasWhiteSpace);
        System.out.println(hasWhiteSpace.replaceAll("\\s", "")); // boşlukları sıfır uzunluklu string ile değiştirir.
        System.out.println(hasWhiteSpace.replaceAll("\\t", "X")); // tab karakteri sıfır uzunluklu string ile değiştirir.

        System.out.println(alphanumeric.replaceAll("\\w", "X")); // alpha numerik karakterleri X yapar
        System.out.println(hasWhiteSpace.replaceAll("\\w", "X")); // alpha numerik karakterleri X yapar
        System.out.println(hasWhiteSpace.replaceAll("\\b", "X")); // her kelimenin başına ve sonuna X ekler

        String thirdAlphanumeric = "abcCDhheeeeeeeAeFjdklm123456789";
        System.out.println(thirdAlphanumeric.replaceAll("^abcCDeee", "YYY"));//abcCDeee ile baslayan ifadeyi YYY ile değiştir
        System.out.println(thirdAlphanumeric.replaceAll("^abcCDe{3}", "YYY")); //abcCD ile başlayıp 3 tane e ile devam eden ifadeyi YYY ile değiştir
        System.out.println(thirdAlphanumeric.replaceAll("^abcCDe+", "YYY")); //abcCD ile başlayıp bir veya daha fazla e ile devam eden ifadeyi YYY ile değiştir
        System.out.println(thirdAlphanumeric.replaceAll("^abcCDe*", "YYY")); //abcCD ile başlayıp e ile devam eden veya etmeyen ifadeyi YYY ile değiştir.
        System.out.println(thirdAlphanumeric.replaceAll("^abcCDe{2,5}", "YYY")); //abcCD ile başlayıp  en az 2 tane e ile veya en fazla 5 tane e ile devam eden ifadeyi YYY ile değiştir
        System.out.println(thirdAlphanumeric.replaceAll("h+e*A", "YYY")); //bir veya birden fazla h içeren, hemen yanında e ile başlayan veya başlamayan, ve A ile biten ifadeyi YYY ile değiştir.

        StringBuilder htmlText = new StringBuilder("<h1>My heading</h1>");
        htmlText.append("<h2>Sub heading</h2>");
        htmlText.append("<p>a paragraph about something</p>");
        htmlText.append("<h2>Summary</h2>");
        htmlText.append("<p>here is the summary </p>");

        Pattern pattern = Pattern.compile(".*<h2>.*"); // <h2> içeren pattern
        Matcher matcher = pattern.matcher(htmlText);
        System.out.println(matcher.matches());

        Pattern pattern2 = Pattern.compile("<h2>");
        Matcher matcher2 = pattern2.matcher(htmlText);
        System.out.println(matcher2.matches());

        matcher2.reset();
        int count = 0;
        while(matcher2.find()){
            count++;
            System.out.println("Occurence: "+ count + " : "+ matcher2.start() + " to " + matcher2.end());
        }

        Pattern h2GroupPattern = Pattern.compile("(<h2>.*?</h2>)");
        Matcher groupMatcher = h2GroupPattern.matcher(htmlText);
        System.out.println(groupMatcher.matches());

        groupMatcher.reset();

        while (groupMatcher.find()){
            System.out.println("Occurrence: "+ groupMatcher.group(1));
        }

        Pattern h2TextGroupPattern = Pattern.compile("(<h2>)(.+?)(</h2>)");
        Matcher h2TextMatcher = h2TextGroupPattern.matcher(htmlText);
        while (h2TextMatcher.find()){
            System.out.println("Occurence:" + h2TextMatcher.group(2));
        }

    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public GreetingResponse doSomething(String input) {
        GreetingResponse rsp = new GreetingResponse();
        try {
            rsp.setData(input + " tobe manipulated");
            Person person = new Person();
            person.setName(input);

            personRepository.save(person);
            Future<Person> future = asyncService.asyncMethodWithReturnType(person);
           // System.out.println("future.get(): "+ future.get()); // blocked here!!
            rsp.setData(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp;
    }

    @Transactional
    public GreetingResponse doSomething2(String input) throws Exception {

        GreetingResponse rsp = new GreetingResponse();
        try {

            rsp.setData(input + " tobe manipulated");

            Person person = new Person();
            person.setName(input);

            personRepository.save(person);

            asyncService.asyncMethodWithReturnType3(person);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        return rsp;
    }

    @Transactional
    public GreetingResponse doWork(String input)  {

        GreetingResponse rsp = new GreetingResponse();
        try {

            rsp.setData(input + " tobe manipulated");

            Person person = new Person();
            person.setName(input);

            personRepository.save(person);

            asyncService.doWork(person);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        return rsp;
    }
    @Transactional
    public Person savePerson(String input)  {
        try {
            Person person = new Person();
            person.setName(input);
            return personRepository.save(person);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
