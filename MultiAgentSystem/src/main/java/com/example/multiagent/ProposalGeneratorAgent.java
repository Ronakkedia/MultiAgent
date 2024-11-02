package com.example.multiagent;

import java.util.*;

public class ProposalGeneratorAgent {
    public String generateProposal(String companyName, List<String> industryTrends, String useCases, List<String> datasets) {
        StringBuilder proposal = new StringBuilder();
        proposal.append("## ").append(companyName).append(" AI & GenAI Use Case Proposal\n\n")
                .append("### Industry Trends\n");

        for (String trend : industryTrends) {
            proposal.append("- ").append(trend).append("\n");
        }

        proposal.append("\n### Proposed Use Cases\n").append(useCases).append("\n")
                .append("\n### Resource Assets\n");

        for (String dataset : datasets) {
            proposal.append("- ").append(dataset).append("\n");
        }

        return proposal.toString();
    }
}