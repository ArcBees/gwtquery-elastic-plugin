/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gquery.elastic.widgetsample.client;

import com.arcbees.gquery.elastic.client.Elastic;
import com.arcbees.gquery.elastic.client.ElasticHtmlPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Random;

public class WidgetSample implements EntryPoint {
    interface WidgetSampleUiBinder extends UiBinder<Widget, WidgetSample> {
    }

    private static final WidgetSampleUiBinder UI_BINDER = GWT.create(WidgetSampleUiBinder.class);
    private static final int SCROLL_TRESHOLD = 400;
    private static final int ITEMS_INCREMENT = 10;
    private static final String[] LOREM_IPSUM = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus imperdiet elit augue, sed tempus mi pretium vel. In hac habitasse platea dictumst. Nullam egestas sit amet leo ac eleifend. Etiam euismod volutpat turpis ut venenatis. Duis bibendum nibh id faucibus vulputate. Aliquam at elit id dolor iaculis malesuada at at leo. In blandit tortor et aliquam bibendum. Mauris porttitor elit a leo luctus aliquam. Nam ipsum mauris, eleifend eu facilisis et, auctor quis metus. Nunc sollicitudin mattis mattis.",
            "Vestibulum sodales scelerisque enim in vehicula. Duis scelerisque vitae magna in lacinia. Phasellus non porta metus, in rhoncus elit. Proin eu tincidunt velit. Integer accumsan, justo id semper laoreet, nulla lacus facilisis leo, ac commodo nisi risus nec nibh. Fusce sed molestie enim. Duis luctus justo in justo tristique, nec sodales justo molestie.",
            "Curabitur nec sem et nisi interdum dapibus eleifend id orci. Quisque tristique cursus dolor nec congue. Quisque tincidunt feugiat dolor, non semper purus mattis nec. Morbi convallis tempus velit vel semper. Vestibulum mauris lorem, elementum quis diam id, hendrerit pulvinar diam. Vestibulum nec purus vitae nisl viverra egestas. Nam non nibh sit amet velit blandit lobortis sit amet ac ligula. Nunc ac erat ante. Suspendisse ac mauris quis lacus faucibus semper elementum quis magna. Suspendisse mollis, urna sit amet placerat euismod, metus nunc scelerisque justo, quis rhoncus odio nulla non arcu. Duis eu sem et nisi viverra tincidunt vitae eget risus. In hendrerit a ligula eu scelerisque. In consequat mauris id hendrerit convallis. Suspendisse nec feugiat magna. Fusce eget ante nec sapien feugiat volutpat.",
            "In congue, nisi sit amet placerat interdum, eros justo imperdiet tellus, a dapibus tellus mauris vel lorem. Etiam in massa a diam egestas euismod. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Integer a rhoncus mi. Sed blandit libero vulputate ipsum facilisis, sit amet tristique mi faucibus. Morbi eleifend venenatis libero, a varius ligula lacinia eget. In hac habitasse platea dictumst. Curabitur non mattis nunc. Ut lobortis rhoncus orci feugiat hendrerit.",
            "Cras eu purus congue, commodo nibh in, mattis velit. Duis tristique faucibus ante eu feugiat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris congue lorem id urna ultricies, at gravida dui auctor. Phasellus a tortor sed dolor tempus gravida non sit amet erat. Quisque congue, quam porttitor malesuada accumsan, erat erat facilisis neque, ut convallis lacus metus at urna. Fusce aliquam cursus tempor. Phasellus vel turpis eget nibh ultrices volutpat congue eu purus. Mauris eget neque ut lorem mollis dictum. Proin convallis quis ligula ac tempor. Suspendisse potenti.",
            "Nullam faucibus magna ac neque adipiscing hendrerit. Donec tellus urna, vestibulum id nisi at, interdum vestibulum nisi. Sed eu diam sapien. Vivamus a rhoncus ligula, et congue sapien. Quisque eget aliquam sapien. Phasellus volutpat mauris justo, quis laoreet tortor imperdiet venenatis. Quisque in pulvinar justo. Morbi imperdiet nunc vitae rhoncus tempor. Praesent posuere erat eget consectetur pellentesque. Nullam eleifend fringilla magna tristique condimentum. Praesent eu turpis et nisi vulputate consectetur. Maecenas imperdiet feugiat turpis, vel porttitor lacus. Nam pharetra est bibendum risus molestie faucibus. Vestibulum posuere ultricies arcu, id auctor enim sagittis ac. Nullam convallis eros at libero commodo dapibus.",
            "Nulla facilisi. Proin nec magna mollis, porta urna eu, commodo felis. Duis vulputate est a nibh vestibulum, eget imperdiet diam pharetra. Vivamus vestibulum est sed malesuada tristique. Donec quis odio ipsum. Quisque sit amet volutpat risus, a tincidunt tellus. Duis eu velit vitae orci accumsan pharetra quis eu odio. Donec venenatis odio sit amet tellus pulvinar hendrerit. Phasellus luctus rutrum iaculis. Duis consequat dui vel venenatis pharetra. Duis commodo libero nisl, sed posuere augue imperdiet vel. Maecenas magna sapien, elementum eu lorem ut, tincidunt posuere enim. Fusce euismod nibh quis est dapibus porta.",
            "Cras a nunc pretium, cursus dolor sit amet, tincidunt diam. Phasellus iaculis magna eu adipiscing facilisis. Nunc sit amet pharetra magna. Vestibulum eget mi tempus ligula commodo euismod. Vivamus fringilla orci a enim vehicula eleifend. Maecenas quis quam dapibus, ultrices odio vitae, consequat urna. Aenean orci purus, lacinia sit amet diam at, venenatis commodo dui. Fusce interdum, quam eget sollicitudin pellentesque, ante urna pharetra purus, eu ullamcorper orci nibh quis sem.",
            "Morbi in tortor semper, venenatis lectus vel, interdum felis. Praesent lacus augue, elementum eu dolor in, tempor volutpat sem. Donec at ornare augue, sit amet venenatis lacus. Pellentesque tincidunt fermentum lectus sit amet adipiscing. Praesent eget egestas purus, non pretium tortor. Suspendisse tellus turpis, vulputate sed mauris et, pulvinar auctor turpis. Pellentesque congue, metus sit amet accumsan aliquet, velit mi ultricies nibh, at porttitor dui neque porttitor lacus. Duis sagittis elit vitae nisl ultrices, ac porttitor lectus ultricies. Ut non condimentum lorem.",
            "Suspendisse vitae purus ut sem euismod luctus. Suspendisse placerat mattis neque nec congue. Proin eu felis pulvinar, varius lorem at, ultrices orci. Ut mattis lacus et posuere rutrum. Proin non commodo metus, vitae tempus lorem. Vivamus consequat, tellus pulvinar condimentum commodo, urna massa convallis ligula, ornare placerat massa ante id nulla. Fusce auctor convallis augue, nec ultricies tellus cursus sed. Vivamus at libero eget lorem pellentesque posuere. Etiam tempor, justo eu placerat imperdiet, nulla lacus bibendum nulla, sed convallis elit est quis massa. Sed ut pharetra orci. Pellentesque vel nibh consectetur, semper nisi in, pharetra justo.",
            "Nam dapibus pellentesque felis, et sodales dolor sollicitudin eu. Suspendisse facilisis et metus ac feugiat. Nulla tincidunt eros magna, in congue leo placerat sit amet. Nunc vel dolor ut ligula commodo iaculis. Fusce rutrum mattis ante nec mollis. Pellentesque eu eleifend dolor, vel fringilla nisi. Mauris eu augue convallis, adipiscing nulla a, dapibus mi. Donec lobortis dapibus justo nec eleifend. Duis mollis libero vel congue auctor. In feugiat est lectus, in dignissim nulla facilisis nec.",
            "Integer accumsan volutpat diam pretium pharetra. Sed et dui eget nisi scelerisque fermentum. Sed euismod urna eu vehicula elementum. Integer quis feugiat metus, et eleifend nisl. Sed quis magna nisi. Donec accumsan orci libero, in dapibus eros vulputate non. Curabitur ullamcorper, erat non elementum tempus, nibh metus consectetur lacus, et mollis sem neque id quam. In mollis sagittis augue. Curabitur id velit luctus, elementum purus a, euismod nibh. Donec condimentum condimentum nisi ac malesuada. Sed imperdiet ante eu urna pharetra, ut adipiscing turpis congue. Nam mattis ultricies est, id sagittis purus pharetra vel.",
            "Nunc et diam turpis. Nulla eget suscipit tortor, ut blandit tellus. Donec fermentum faucibus ullamcorper. Cras placerat mauris dui, in adipiscing dolor molestie at. Suspendisse molestie at lectus in gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin ullamcorper ipsum non facilisis lobortis. Morbi ante sem, ultricies et dapibus non, pharetra in neque. Mauris ullamcorper, ligula a molestie cursus, nunc nulla tristique leo, vel venenatis est dolor sit amet lectus. Donec vel dolor quam. Vivamus odio ante, faucibus at nibh non, tristique facilisis nisl. Nunc aliquet ligula eu ante auctor, id accumsan nulla faucibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras a eros consequat, porta ipsum id, gravida augue.",
            "Sed eget nisl in diam ornare hendrerit in quis neque. Nullam porta vulputate metus, eu laoreet augue vulputate vel. Etiam non semper nulla. In id sagittis justo, ut lobortis massa. Nulla nulla mi, placerat ut dolor vitae, blandit elementum ante. Praesent elementum leo vel nisl volutpat, eget laoreet turpis luctus. Vivamus adipiscing pellentesque velit ut vehicula. Curabitur tempus quam felis, ac luctus risus placerat facilisis. Nam sagittis neque non leo consectetur faucibus. Aenean pretium, libero sed euismod scelerisque, velit dui malesuada nisi, et semper tellus elit condimentum dui. Nulla pharetra orci in vestibulum sagittis. Curabitur semper enim et neque imperdiet sodales. Nulla facilisi. Aliquam risus nisi, faucibus non sapien at, dapibus tempor ipsum. Curabitur nec dolor sollicitudin, sodales metus semper, cursus velit.",
            "Donec at sem arcu. Suspendisse ut mattis sem. Mauris porttitor, nisl in bibendum pellentesque, sem turpis pulvinar nibh, et suscipit lacus nulla in diam. Nunc eget rutrum mi. Sed laoreet tellus at purus hendrerit vehicula. Maecenas quam turpis, condimentum sed varius at, lacinia sed ante. Nulla condimentum nisl quis magna mollis, nec fringilla augue sagittis.",
            "Sed sed tincidunt urna, nec tristique mauris. Maecenas dignissim nibh malesuada quam auctor pharetra. Curabitur a rutrum tortor, a dapibus felis. Duis et ligula dui. Nam blandit elit ac orci cursus feugiat. In venenatis tortor eget est convallis viverra sit amet nec felis. Integer porta, ligula non euismod luctus, tortor libero dignissim neque, eu ornare quam odio id ipsum. Donec quis massa bibendum, laoreet diam sit amet, vulputate diam. Duis diam tortor, feugiat sit amet turpis ut, faucibus pulvinar nisi. Proin auctor, justo in tristique interdum, mi magna elementum metus, nec tincidunt augue dui in purus. Sed commodo quam eget suscipit interdum. Nunc quis dolor eu elit convallis lobortis. Nunc imperdiet purus magna, sed pharetra nibh laoreet et.",
            "Cras tincidunt pretium pretium. Nullam in massa in augue molestie tincidunt et sit amet turpis. Pellentesque in dolor posuere, euismod risus id, egestas diam. Vivamus congue, leo sit amet convallis aliquam, risus enim cursus ante, eu vehicula urna felis id mauris. Vivamus aliquet condimentum tortor vel gravida. Ut facilisis arcu imperdiet, placerat erat quis, rhoncus nisi. Nulla porta volutpat vestibulum. Integer dolor leo, consequat faucibus urna sed, posuere condimentum metus.",
            "Ut luctus est consequat, fringilla eros ut, lobortis enim. Sed molestie rhoncus leo, nec ultrices dolor pellentesque sit amet. Aliquam sit amet justo at turpis dignissim mollis id a felis. Cras vel adipiscing tortor. Etiam et tincidunt dui. Donec nulla mauris, pretium eu tempor ut, vestibulum eget felis. In egestas eu lectus id imperdiet. Donec blandit et augue vitae vulputate. Donec at urna elit. Vestibulum at vehicula nisi. Donec iaculis sed lacus in fermentum. Praesent eu erat lacus. Pellentesque dictum mauris ac dolor facilisis, sed ullamcorper orci auctor.",
            "Donec imperdiet eu magna id cursus. Quisque dictum euismod bibendum. Aenean hendrerit hendrerit velit in consectetur. Etiam sollicitudin lectus sit amet pellentesque tincidunt. Cras convallis placerat hendrerit. Aenean ornare tempus odio, faucibus congue est scelerisque nec. Sed consequat justo gravida turpis condimentum luctus. Quisque ornare nisi eget tellus fringilla tempus. Integer gravida sem at lacinia porttitor. Donec in sagittis risus.",
            "Integer aliquet velit tempus erat cursus, et feugiat metus pharetra. Sed quis blandit dolor. In dignissim libero sit amet ipsum vulputate, vitae adipiscing erat sollicitudin. Fusce imperdiet felis ut mollis tristique. Nam lobortis tincidunt vulputate. Vestibulum mollis ornare varius. Nam mattis lectus a tortor tincidunt iaculis. Quisque accumsan velit eget erat mollis, et tempus velit dictum. Nam pulvinar elementum placerat. Ut facilisis mi ac orci vehicula facilisis. Sed ornare orci fermentum turpis pellentesque, in tincidunt nibh rutrum. Morbi ultrices nisl feugiat nisi pulvinar, lobortis adipiscing dui consequat. Vestibulum diam ipsum, condimentum eget nibh a, tempus iaculis tortor.",
            "Suspendisse sagittis, magna a molestie posuere, urna elit laoreet lorem, eu auctor nibh eros eu ligula. Cras id malesuada neque. Donec non nisi in ipsum mattis ornare quis tempor elit. Fusce ultricies dictum nunc, ac commodo elit ultricies non. Mauris gravida, dui ut scelerisque varius, tellus massa posuere ipsum, in suscipit lacus odio in risus. Curabitur ligula urna, sodales sed magna imperdiet, pharetra interdum neque. Suspendisse in venenatis lectus, eu commodo tortor. Aliquam lacinia ornare sem, vel posuere mauris imperdiet in. Aliquam eleifend at est a ullamcorper. Nam non interdum lorem. Praesent elementum arcu a diam tempus, a tristique quam ultrices. Morbi varius bibendum massa, in pharetra enim rutrum nec. Donec ornare velit sit amet velit pretium tincidunt. Suspendisse blandit eu lacus sed malesuada. Donec auctor ultrices neque. Pellentesque tempus at dolor pulvinar aliquet.",
            "Nam non quam vel eros porta facilisis. Mauris vel erat nisl. Aenean lobortis varius risus. Ut nec suscipit justo. Mauris ullamcorper sapien massa, vitae bibendum lacus consectetur non. Fusce egestas facilisis congue. Donec a vulputate massa. Aliquam a varius ipsum.",
            "Morbi et neque vitae lectus gravida scelerisque. Ut viverra nisl rhoncus ante suscipit tincidunt. Nunc varius est est, sit amet venenatis nibh condimentum nec. Pellentesque eget mauris bibendum, scelerisque ligula vitae, eleifend purus. Nunc commodo purus sit amet convallis adipiscing. Aenean at augue a metus tristique pulvinar. Etiam sed dapibus orci, eu eleifend mauris. Morbi quis leo felis. Nullam eu sagittis magna. Donec hendrerit tellus id odio interdum eleifend. Praesent non ullamcorper nunc. Donec sit amet feugiat quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nam sit amet nisl et velit laoreet pretium. Nullam pulvinar tortor viverra malesuada ultricies. Aenean laoreet dolor vitae odio fermentum aliquet.",
            "Morbi lobortis fermentum lacinia. Nullam lectus dolor, tincidunt in bibendum in, mattis non lorem. In hac habitasse platea dictumst. Cras in nisl vestibulum, sollicitudin tellus at, tincidunt est. Aenean in lorem sed libero ultricies semper nec eu lorem. Integer sit amet justo tincidunt, porta ante non, ullamcorper neque. Curabitur congue tortor eu massa interdum, sed condimentum velit dignissim. Phasellus porta consectetur interdum. Curabitur euismod, nibh non dictum dictum, tortor mi egestas lacus, non eleifend ligula ipsum non elit.",
            "Pellentesque mauris turpis, lobortis id malesuada in, ultricies sed ante. Donec eros arcu, rutrum non nisl feugiat, tincidunt faucibus sem. Phasellus non gravida magna. Mauris quis nunc sit amet tortor pulvinar laoreet. Donec lacinia sapien lectus, vitae egestas odio luctus id. Donec placerat dignissim elit ut congue. Phasellus in posuere diam, ac adipiscing arcu. Mauris lectus nisi, dictum quis justo id, bibendum lobortis nulla. Sed purus velit, lacinia a aliquet non, tristique eu urna. Nam nibh nisi, imperdiet sed sem vitae, malesuada convallis mauris. Praesent et dapibus tellus.",
            "Nulla ut nisl vel dolor pulvinar consequat vel in lectus. Nulla sodales est ac diam tincidunt viverra. Sed quam lectus, aliquam ut mollis nec, mattis elementum magna. Nam ante neque, volutpat ut erat ut, gravida ultricies erat. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer consectetur malesuada facilisis. Sed vel dui vel orci cursus sollicitudin ac at metus. Praesent facilisis est vel eros vulputate, ac sagittis massa vestibulum.",
            "Praesent vulputate posuere metus, venenatis bibendum erat. Ut convallis diam in ornare porttitor. Vivamus sit amet euismod eros. Morbi non mauris vel eros aliquam commodo nec eget dui. Nunc viverra leo vel dui aliquet, suscipit volutpat magna pharetra. Etiam lobortis ipsum id felis malesuada, vel tincidunt dolor placerat. Suspendisse facilisis pretium pulvinar. Curabitur at porttitor ante. Sed rutrum, turpis blandit commodo sollicitudin, lorem purus sollicitudin ante, quis dapibus orci ligula id tellus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Ut fringilla nibh magna. Vestibulum vel mi velit. Nullam posuere a turpis a aliquam. Integer condimentum luctus diam suscipit tempus. Ut blandit fringilla sem, id tempus ligula semper sed. Proin id enim ultricies, sagittis orci at, pulvinar purus. Quisque elementum nulla a dolor vehicula, vehicula sollicitudin purus viverra. Nulla id sollicitudin dolor. Duis at mi eget ligula interdum consequat in vitae sapien. Sed vitae bibendum nulla, feugiat viverra nulla. Praesent interdum, tortor et laoreet bibendum, dui orci tempus velit, a porta ipsum sem gravida ligula. Fusce sagittis consequat turpis nec venenatis. Proin orci nisl, congue vitae malesuada nec, pellentesque sed risus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Mauris at orci dapibus, fermentum odio et, sagittis dolor. Vivamus magna diam, dignissim id porttitor id, condimentum a odio.",
            "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Curabitur a nibh dignissim mauris facilisis ultrices. Nulla venenatis porta adipiscing. Suspendisse potenti. Mauris bibendum posuere mauris, quis feugiat nunc imperdiet eu. Nunc aliquet mauris eget blandit ornare. Pellentesque pretium eu massa nec bibendum. Morbi suscipit ullamcorper est sit amet adipiscing.",
            "Nam nec magna molestie ante iaculis eleifend. In vel tortor eleifend augue hendrerit placerat non sit amet ante. Fusce laoreet accumsan est non rhoncus. Aliquam tristique libero quis lorem viverra bibendum. Integer consectetur posuere metus, nec blandit sem dignissim ut. Donec vel dapibus ipsum. Nullam at malesuada nulla. Sed vitae volutpat odio."};


    @UiField
    ScrollPanel scrollPanel;
    @UiField
    ElasticHtmlPanel htmlPanel;

    private final Random rand = new Random();

    private int lastScrollPosition;
    private int singleItemNumber;
    private int itemsCounter = 10;

    public WidgetSample() {
        UI_BINDER.createAndBindUi(this);

        addNewItems(20);
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get("main").add(scrollPanel);
    }

    @UiHandler("scrollPanel")
    void onScroll(ScrollEvent event) {
        int oldScrollPos = lastScrollPosition;
        lastScrollPosition = scrollPanel.getVerticalScrollPosition();
        if (oldScrollPos >= lastScrollPosition) {
            return;
        }

        int maxScrollTop = scrollPanel.getWidget().getOffsetHeight()
                - scrollPanel.getOffsetHeight() - SCROLL_TRESHOLD;
        if (lastScrollPosition >= maxScrollTop) {
            addNewItems(ITEMS_INCREMENT);
        }
    }

    private void addNewItems(int itemsNumber) {
        for (int i = 0; i < itemsNumber; i++) {
            addItem();
        }
    }

    private void addItem() {
        HTML html = new HTML();

        if (singleItemNumber > 5 && rand.nextBoolean() && rand.nextBoolean()) {
            // add an item that span 2 or 3 column
            html.getElement().setAttribute(Elastic.SPAN_ATTRIBUTE, "2");
            singleItemNumber = 0;
        } else {
            singleItemNumber++;
        }

        int nbrParagraph = 1 + rand.nextInt(3); // up to 3 paragraph
        StringBuilder content = new StringBuilder().append(itemsCounter++).append(". ");

        for (int i = 0; i < nbrParagraph; i++) {
            content.append(LOREM_IPSUM[rand.nextInt(LOREM_IPSUM.length)]).append("<br/>");
        }

        html.setHTML(content.toString());

        htmlPanel.add(html);
    }
}
