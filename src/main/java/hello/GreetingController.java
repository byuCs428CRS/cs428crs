package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public @ResponseBody Greeting greeting(
      @RequestParam(value="name", required=false, defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
        String.format(template, name));
  }

  @RequestMapping(value = "/greeting/v2/test", method = RequestMethod.GET)
  public @ResponseBody TestIt testIt() {
    return new TestIt();
  }
}