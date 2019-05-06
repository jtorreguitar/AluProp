package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.RuleDao;
import ar.edu.itba.paw.interfaces.service.RuleService;
import ar.edu.itba.paw.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class APRuleService implements RuleService {

    @Autowired
    private RuleDao ruleDao;

    @Override
    public Collection<Rule> getAll() {
        return ruleDao.getAll();
    }
}
