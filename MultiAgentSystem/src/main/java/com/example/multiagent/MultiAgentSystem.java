package com.example.multiagent;

import java.io.IOException;
import java.util.*;

public class MultiAgentSystem {
    public static void main(String[] args) {
        String companyNameOrIndustry = "Supply Chain Optimization ";

        ResearchAgent researchAgent = new ResearchAgent();
        UseCaseGeneratorAgent useCaseAgent = new UseCaseGeneratorAgent();
        DatasetCollectorAgent datasetAgent = new DatasetCollectorAgent();
        ProposalGeneratorAgent proposalAgent = new ProposalGeneratorAgent();

        try {
            List<String> industryTrends = researchAgent.fetchIndustryTrends(companyNameOrIndustry);
            String combinedTrends = String.join("\n", industryTrends);
            String useCases = useCaseAgent.generateUseCaseUsingCohere(combinedTrends);
            List<String> datasets = datasetAgent.searchDatasets(useCases,companyNameOrIndustry);
            String proposal = proposalAgent.generateProposal(companyNameOrIndustry, industryTrends, useCases, datasets);

            System.out.println(proposal);

        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
}