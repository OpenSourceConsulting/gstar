package com.gemmystar.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gemmystar.api.domain.CommonCode;
import com.gemmystar.api.domain.CommonCodeRepository;

/**
 * 공통 코드 목록 전역 저장소.
 *
 * Created on 2010. 7. 15
 * 
 * @author 권봉진
 */
@Service
public class CommonCodeService implements InitializingBean {

	protected final Logger logger = LoggerFactory.getLogger(CommonCodeService.class);

	Map<String, ArrayList<CommonCode>> allCodes = new HashMap<String, ArrayList<CommonCode>>();

	@Autowired
	protected CommonCodeRepository repository;


	public CommonCodeService() {
	}

	/**
	 * <pre>
	 * group code 가져오기.
	 * </pre>
	 *
	 * @return
	 */
	/*
	 * public ArrayList getGroups(){
	 * 
	 * Map allCodes = getAllCodes();
	 * 
	 * return (ArrayList)allCodes.get(CODE_GROUPS); }
	 */

	/**
	 * cd_grp 의 코드리스트를 반환한다.
	 * 
	 * @param cd_grp
	 * @return
	 */
	public List<CommonCode> getCodes(String group_cd) {

		Map allCodes = getAllCodes();
		if (allCodes == null)
			return null;

		List<CommonCode> codes = (ArrayList) allCodes.get(group_cd);

		if (codes == null) {
			// 캐시에 없으면 db query.
			try {
				codes = repository.findByGroupCd(group_cd);
				// StringUtil.changeModelListCharset(codes, new
				// String[]{"cdnm"});
				allCodes.put(group_cd, codes);
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
		}

		// return (ArrayList)allCodes.get(cd_grp);
		return codes;
	}

	@SuppressWarnings("unchecked")
	private Map getAllCodes() {

		return allCodes;
	}

	/*
	 * public static void setCodes(String group_cd, ArrayList codes){
	 * allCodes.put(group_cd, codes); }
	 */

	/**
	 * 해당 코드 정보 가져오기.
	 * 
	 * @param group_cd
	 * @param code
	 * @return
	 */
	public CommonCode getCode(String group_cd, String code) {
		List<CommonCode> codes = getCodes(group_cd);
		CommonCode codef = null;

		if (codes != null) {
			for (CommonCode commonCode : codes) {
				codef = commonCode;
				if (codef.getCode().equals(code)) {
					break;
				} else {
					codef = new CommonCode();
				}
			}
		}

		return codef;
	}

	/**
	 * <pre>
	 * 코드명 가져오기.
	 * </pre>
	 *
	 * @param group_cd
	 * @param code
	 * @return
	 */
	public String getCodeNm(String group_cd, String code) {
		CommonCode codef = getCode(group_cd, code);

		if (codef == null) {
			logger.debug("{}, {} is not found.", group_cd, code);
			return "";
		}

		if (codef.getCodeNm() == null) {
			logger.debug("{}, {} code name is not found.", group_cd, code);
		}

		return codef.getCodeNm();
	}


	@SuppressWarnings("unchecked")
	private void loadCodesProcess() {
		logger.info("공통 코드 loading...");

		List<CommonCode> list = new ArrayList<CommonCode>();

		try {
			list = repository.findAll(new Sort(Sort.Direction.ASC, "groupCd", "orderSeq"));

		} catch (Exception e) {

			logger.error(e.toString(), e);
		}

		ArrayList<CommonCode> codes = null;
		int cntCodes = 0;

		String cd_grp = "";
		for (CommonCode codef : list) {

			String temp = codef.getGroupCd();

			if (!cd_grp.equals(temp)) {
				codes = new ArrayList<CommonCode>();
				allCodes.put(temp, codes);
			}

			cntCodes++;
			codes.add(codef);
			cd_grp = temp;
		}

		logger.info("공통 코드 완료 (총: {}개).", cntCodes);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadCodesProcess();
	}

}
