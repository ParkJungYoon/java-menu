package menu.controller;

import menu.domain.CoachRepository;
import menu.view.InputView;
import menu.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

import static menu.util.TransformInput.splitInput;

public class MenuController {
    public void run() {
        setUp();
    }

    private void setUp() {
        CoachRepository.nameRegistration(initCoachNames());
        initHateMenu();
    }

    private void initHateMenu() {
        List<String> names = CoachRepository.getCoachNames();
        for (String name : names) {
            String menu = InputView.readHateMenu(name);
            CoachRepository.hateMenuRegistration(name, splitInput(menu));
        }
    }

    private List<String> initCoachNames() {
        String names = repeat(InputView::readCoachName);
        return splitInput(names);
    }

    private <T> T repeat(Supplier<T> inputReader) {
        try {
            return inputReader.get();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return repeat(inputReader);
        }
    }
}