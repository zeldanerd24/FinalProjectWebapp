package com.krothenberger.controller;

import com.krothenberger.model.FactRequest;
import com.krothenberger.model.Results;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Kevin on 4/21/2015.
 */

@Controller
@RequestMapping(value = "/factorial")
public class FactorialController {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(FactorialController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String viewForm(Map<String, Object> model) throws Exception {

        FactRequest factRequest = new FactRequest();
        model.put("factRequest", factRequest);
        model.put("ipaddress", InetAddress.getLocalHost().toString());
        // Spring uses InternalResourceViewResolver and return back index.jsp
        return "index";

    }

    @RequestMapping(method = RequestMethod.POST)
    public String returnFact(@ModelAttribute("factRequest") FactRequest factRequest,
                             Map<String, Object> model) throws Exception {
        Results results = new Results();
        results.setNumber(factRequest.getNumber());
        results.setHostIp(InetAddress.getLocalHost().toString());

        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier("9f:6b:5d:ad:9b:68:26:fb:88:0e:8d:8d:2f:b9:61:f6");
        sshClient.addHostKeyVerifier("69:67:78:6c:c3:ee:84:c1:5d:bc:e7:9a:66:43:07:bb");

        long startTime = System.currentTimeMillis();

        sshClient.connect("10.0.0.7", 22);

        String result;
        String ip;

        try {
            sshClient.authPassword("krothenberger", "Blackhole24");
            Session session = sshClient.startSession();
            try {
                Session.Command command = session.exec("./scripts/factfunction/factorial.py");
                result = IOUtils.readFully(command.getInputStream()).toString();
                Session.Command ipCommand = session.exec("hostname -I");
                ip = IOUtils.readFully(ipCommand.getInputStream()).toString();
            } finally {
                session.close();
            }

        } finally {
            sshClient.disconnect();
        }

        long endTime = System.currentTimeMillis();

        results.setTime(endTime-startTime);
        results.setResult(result);
        results.setComputeIp(ip);

        model.put("results", results);
        return "result";

    }

}
