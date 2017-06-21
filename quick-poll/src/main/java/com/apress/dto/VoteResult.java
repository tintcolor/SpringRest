package com.apress.dto;

import java.util.Collection;

/**
 * Created by anthonyjones on 6/20/17.
 */
public class VoteResult {

    private int totalVotes;
    private Collection<OptionCount> result;

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Collection<OptionCount> getResult() {
        return result;
    }

    public void setResult(Collection<OptionCount> result) {
        this.result = result;
    }
}
