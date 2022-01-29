package adaptor.multiinterface.improve;

import adaptor.multiinterface.ASensitiveWordsFilter;

import java.util.ArrayList;
import java.util.List;

public class ASensitiveWordsFilterAdaptor implements ISensitiveWordsFilter {
    private ASensitiveWordsFilter aFilter;
    public String filter(String text) {
        String maskedText = aFilter.filterSexyWords(text);
        maskedText = aFilter.filterPoliticalWords(maskedText);
        return maskedText;
    }
}
//...省略BSensitiveWordsFilterAdaptor、CSensitiveWordsFilterAdaptor...



