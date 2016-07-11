package com.android.pluginapk.util;

/**
 * @author wangduo
 * @description: ${todo}
 * @email: cswangduo@163.com
 * @date: 16/7/11
 */
public class TaskResult<T> {

	private T value;
	private TaskResultCode retCode = TaskResultCode.UN_KNOWN;

	public TaskResult() {
	}

	public TaskResult(final TaskResultCode code) {
		this.retCode = code;
	}

	/**
	 * @return the value
	 */
	public T getValue() {

		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return the retCode
	 */
	public TaskResultCode getRetCode() {
		return this.retCode;
	}

	/**
	 * @param retCode
	 *            the retCode to set
	 */
	public void setRetCode(TaskResultCode retCode) {
		this.retCode = retCode;
	}

}
