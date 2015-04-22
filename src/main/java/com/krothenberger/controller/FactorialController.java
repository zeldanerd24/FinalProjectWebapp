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
        int number = factRequest.getNumber();

        Results results = new Results();
        results.setNumber(number);
        results.setHostIp(InetAddress.getLocalHost().toString());

        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier("c4:5a:43:e4:86:32:fb:7b:49:4b:ee:99:7e:5f:d4:30");
        sshClient.addHostKeyVerifier("f5:c3:5b:1a:64:73:99:05:15:29:0b:d9:f9:2f:a6:74");

        long startTime = System.currentTimeMillis();

        sshClient.connect("10.0.0.7", 22);

        String result;
        String ip;

        try {
            sshClient.authPassword("krothenberger", "Blackhole24");
            Session session = sshClient.startSession();
            try {
                Session.Command command = session.exec("./scripts/factfunction/factorial.py " + number);
                result = IOUtils.readFully(command.getInputStream()).toString();
            } finally {
                session.close();
            }
            session = sshClient.startSession();
            try {
                Session.Command ipCommand = session.exec("hostname -I");
                ip = IOUtils.readFully(ipCommand.getInputStream()).toString();
            } finally {
                session.close();
            }

        } finally {
            sshClient.disconnect();
        }

        long endTime = System.currentTimeMillis();

        StringBuilder resultWithSpaces = new StringBuilder();

        int i;
        for(i=0; i+20 < result.length(); i+=20) {
            resultWithSpaces.append(result.substring(i, i+20));
            resultWithSpaces.append("\n");
        }
        resultWithSpaces.append(result.substring(i));

        results.setTime(endTime-startTime);
        results.setResult(resultWithSpaces.toString());
        results.setComputeIp(ip);

        model.put("results", results);
        return "result";

    }

}
