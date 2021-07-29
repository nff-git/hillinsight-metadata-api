package hillinsight.metadata.api.server;

import focus.spring.extensions.MessageUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/demo")
public class DemoController {

    @RequestMapping(path = "/getDemoString", method = RequestMethod.GET)
    public String getDemoString() {
        return MessageUtils.get("user.loginName");
    }
}
