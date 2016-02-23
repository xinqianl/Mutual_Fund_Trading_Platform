package controller.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.main.Action;
import databean.DetailedFundBean;
import formbean.TransactionDayForm;
import model.Model;
import util.Util;

public class TransactionDayAction extends Action {
	private static final String TRANSACTION_DAY_JSP = "transitionday.jsp";
	private static final String NAME = "employee-transition-day.do";
	private FormBeanFactory<TransactionDayForm> formBeanFactory;
	private Model model;

	public TransactionDayAction(Model model) {

		this.model = model;
		formBeanFactory = FormBeanFactory.getInstance(TransactionDayForm.class);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		try {
			request.setAttribute("customer", null);
			request.setAttribute("errors", errors);

			TransactionDayForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				DetailedFundBean[] detailFundBeans = model.getDetailFunds();
				String lastDate;
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", detailFundBeans);
				return TRANSACTION_DAY_JSP;
			}

			String newDate = form.getDate();
			if (newDate == null || newDate.length() == 0) {
				DetailedFundBean[] detailFundBeans = model.getDetailFunds();
				String lastDate;
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", detailFundBeans);
				errors.add("please input a new date.");
				return TRANSACTION_DAY_JSP;
			}
			String lastDate = model.getLastTransactionDay();
			if (Util.compareDateStrings(lastDate, newDate) > 0) {
				errors.add("Your input put date should be later than last transition day " + lastDate);
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", model.getDetailFunds());
				return TRANSACTION_DAY_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", model.getDetailFunds());
				return TRANSACTION_DAY_JSP;
			}

			String[] ids = form.getIds();
			String[] prices = form.getPrices();

			Map<String, String> map = new HashMap<String, String>();
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					map.put(ids[i], prices[i]);
				}
			}

			model.processTransaction(map, newDate);
			request.setAttribute("message", "Transition day updated");
			lastDate = model.getLastTransactionDay();
			request.setAttribute("lastTransactionDay", lastDate);
			request.setAttribute("minDate", increment(lastDate));
			request.setAttribute("funds", model.getDetailFunds());
			return TRANSACTION_DAY_JSP;
		} catch (RollbackException e) {
			e.printStackTrace();
			errors.add("Invalid date or new funds detected, please go back and refresh");
		} catch (FormBeanException e) {
			e.printStackTrace();
			errors.add(e.toString());
		}
		return "employee-result.jsp";
	}

	private String increment(String newDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(newDate));
		} catch (ParseException e) {

		}
		c.add(Calendar.DATE, 1);
		String res = sdf.format(c.getTime());
		return res;
	}
}
