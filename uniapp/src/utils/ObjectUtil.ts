export function ResetFormData(formData: DataFormType) {
  Object.keys(formData).forEach(key => delete formData[key])
}
