/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/portal/no-global-fetch, no-undef */

(function () {
	if (layoutMode === 'edit') {
		return;
	}

	const caretSpan = fragmentElement.querySelector('#addIntegrationCaret');

	if (caretSpan) {
		caretSpan.innerHTML =
			'<svg class="lexicon-icon lexicon-icon-caret-bottom" viewBox="0 0 512 512">' +
			'<use href="' +
			Liferay.Icons.spritemap +
			'#caret-bottom"></use>' +
			'</svg>';
	}

	const emptyState = fragmentElement.querySelector('#integrationsEmpty');
	const tableContainer = fragmentElement.querySelector('#integrationsTable');
	const tableBody = fragmentElement.querySelector('#integrationsTableBody');

	if (!emptyState || !tableContainer || !tableBody) {
		return;
	}

	function formatDate(dateString) {
		if (!dateString) {
			return '';
		}

		const date = new Date(dateString);

		return date.toLocaleDateString('en-US', {
			day: 'numeric',
			month: 'long',
			year: 'numeric',
		});
	}

	function renderTable(integrations) {
		if (!integrations.length) {
			emptyState.classList.remove('d-none');
			tableContainer.classList.add('d-none');
		}
		else {
			emptyState.classList.add('d-none');
			tableContainer.classList.remove('d-none');

			tableBody.innerHTML = '';

			integrations.forEach((integration) => {
				const row = document.createElement('tr');

				row.innerHTML =
					'<td class="table-cell-expand">' +
					'<span class="table-title">' +
					integration.name +
					'</span>' +
					'</td>' +
					'<td>' +
					'<span class="label label-success">' +
					integration.status +
					'</span>' +
					'</td>' +
					'<td>' +
					integration.dateAdded +
					'</td>' +
					'<td class="text-right">' +
					'<div class="dropdown">' +
					'<button aria-expanded="false" aria-haspopup="true" class="component-action dropdown-toggle integration-kebab" type="button">' +
					'<svg class="lexicon-icon lexicon-icon-ellipsis-v" viewBox="0 0 512 512">' +
					'<use href="' +
					Liferay.Icons.spritemap +
					'#ellipsis-v"></use>' +
					'</svg>' +
					'</button>' +
					'<div class="dropdown-menu dropdown-menu-right">' +
					'<a class="dropdown-item" href="' +
					integration.editUrl +
					'">Edit</a>' +
					'<button class="dropdown-item integration-remove" data-instance-id="' +
					integration.instanceId +
					'" type="button">Remove</button>' +
					'</div>' +
					'</div>' +
					'</td>';

				tableBody.appendChild(row);
			});
		}
	}

	fetch(
		'/o/seo-studio/instances?filter=' +
			encodeURIComponent('googlePageSpeedAPIKey ne null') +
			'&pageSize=1',
		{
			headers: {
				'Accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			const integrations = [];

			const items = data.items || [];

			if (!!items.length && items[0].googlePageSpeedAPIKey) {
				integrations.push({
					dateAdded: formatDate(items[0].dateModified),
					editUrl: '/web/seo-studio/configurations/google-pagespeed',
					instanceId: items[0].id,
					name: 'Google PageSpeed',
					status: 'ACTIVE',
				});
			}

			renderTable(integrations);

			fragmentElement
				.querySelectorAll('.integration-kebab')
				.forEach((kebab) => {
					const menu = kebab.nextElementSibling;

					kebab.addEventListener('click', (event) => {
						event.stopPropagation();

						menu.classList.toggle('show');
					});
				});

			function removeIntegration(instanceId) {
				fetch('/o/seo-studio/instances/' + instanceId, {
					body: JSON.stringify({
						googlePageSpeedAPIKey: '',
					}),
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
					method: 'PATCH',
				})
					.then((response) => {
						if (!response.ok) {
							throw new Error();
						}

						Liferay.Util.openToast({
							message:
								'Google PageSpeed connection has been removed.',
							title: Liferay.Language.get('success'),
							type: 'success',
						});

						renderTable([]);
					})
					.catch(() => {
						Liferay.Util.openToast({
							message: 'Failed to remove integration.',
							type: 'danger',
						});
					});
			}

			function showRemoveModal(instanceId) {
				const overlay = document.createElement('div');

				overlay.className = 'modal-backdrop fade show';

				const modal = document.createElement('div');

				modal.className = 'fade modal show';
				modal.style.display = 'block';
				modal.innerHTML =
					'<div class="modal-dialog modal-dialog-centered modal-warning">' +
					'<div class="modal-content">' +
					'<div class="modal-header" style="background-color: #fff4ec; border-bottom: 2px solid #b95000;">' +
					'<div class="modal-title" style="color: #b95000; font-weight: 600;">' +
					'<svg class="lexicon-icon" style="height: 16px; width: 16px; margin-right: 8px; fill: #b95000;" viewBox="0 0 512 512">' +
					'<path d="M506.3 417l-213.3-364c-16.33-28-57.54-28-73.98 0l-213.2 364C-10.59 444.9 9.851 480 42.74 480h426.6C502.1 480 522.6 445 506.3 417zM232 168c0-13.25 10.75-24 24-24s24 10.75 24 24v128c0 13.25-10.75 24-24 24s-24-10.75-24-24V168zM256 416c-17.36 0-31.44-14.08-31.44-31.44s14.07-31.44 31.44-31.44 31.44 14.08 31.44 31.44S273.4 416 256 416z"></path>' +
					'</svg>' +
					'Remove Google PageSpeed Connection' +
					'</div>' +
					'<button class="btn btn-unstyled close modal-close" type="button">' +
					'<svg class="lexicon-icon" viewBox="0 0 512 512" style="height: 16px; width: 16px;">' +
					'<path d="M295.6 256l206.7-206.7c10.9-10.9 10.9-28.7 0-39.6-10.9-10.9-28.7-10.9-39.6 0L256 216.4 49.3 9.7C38.4-1.2 20.6-1.2 9.7 9.7c-10.9 10.9-10.9 28.7 0 39.6L216.4 256 9.7 462.7c-10.9 10.9-10.9 28.7 0 39.6 5.5 5.5 12.6 8.2 19.8 8.2s14.3-2.7 19.8-8.2L256 295.6l206.7 206.7c5.5 5.5 12.6 8.2 19.8 8.2s14.3-2.7 19.8-8.2c10.9-10.9 10.9-28.7 0-39.6L295.6 256z"></path>' +
					'</svg>' +
					'</button>' +
					'</div>' +
					'<div class="modal-body">' +
					'<p>Removing this Google PageSpeed connection will disconnect access to associated Search Console data in SEO Studio. This action can be reconnected later if needed.</p>' +
					'</div>' +
					'<div class="modal-footer">' +
					'<button class="btn btn-secondary modal-cancel" type="button">Cancel</button>' +
					'<button class="btn modal-confirm" style="background-color: #b95000; border-color: #b95000; color: #fff;" type="button">Remove</button>' +
					'</div>' +
					'</div>' +
					'</div>';

				document.body.appendChild(overlay);
				document.body.appendChild(modal);
				document.body.classList.add('modal-open');

				function closeModal() {
					document.body.removeChild(modal);
					document.body.removeChild(overlay);
					document.body.classList.remove('modal-open');
				}

				modal
					.querySelector('.modal-close')
					.addEventListener('click', closeModal);

				modal
					.querySelector('.modal-cancel')
					.addEventListener('click', closeModal);

				modal
					.querySelector('.modal-confirm')
					.addEventListener('click', () => {
						closeModal();

						removeIntegration(instanceId);
					});
			}

			fragmentElement
				.querySelectorAll('.integration-remove')
				.forEach((removeBtn) => {
					removeBtn.addEventListener('click', () => {
						const instanceId =
							removeBtn.getAttribute('data-instance-id');

						showRemoveModal(instanceId);
					});
				});
		})
		.catch(() => {
			renderTable([]);
		});

	const dropdownToggle = fragmentElement.querySelector('.dropdown-toggle');
	const dropdownMenu = fragmentElement.querySelector('.dropdown-menu');

	if (dropdownToggle && dropdownMenu) {
		dropdownToggle.addEventListener('click', (event) => {
			event.stopPropagation();

			const isOpen = dropdownMenu.classList.contains('show');

			dropdownMenu.classList.toggle('show');
			dropdownToggle.setAttribute('aria-expanded', !isOpen);
		});

		document.addEventListener('click', () => {
			dropdownMenu.classList.remove('show');
			dropdownToggle.setAttribute('aria-expanded', 'false');

			fragmentElement
				.querySelectorAll('.integration-kebab + .dropdown-menu')
				.forEach((menu) => {
					menu.classList.remove('show');
				});
		});
	}

	const toastMessage = sessionStorage.getItem('seoStudioToast');

	if (toastMessage) {
		sessionStorage.removeItem('seoStudioToast');

		Liferay.Util.openToast({
			message: toastMessage,
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	}

	const collapseToggle = fragmentElement.querySelector(
		'.integrations-collapse-toggle'
	);
	const body = fragmentElement.querySelector('#integrationsBody');
	const header = fragmentElement.querySelector('.integrations-header');

	if (collapseToggle && body && header) {
		collapseToggle.addEventListener('click', () => {
			const isExpanded =
				collapseToggle.getAttribute('aria-expanded') === 'true';

			collapseToggle.setAttribute('aria-expanded', !isExpanded);
			body.classList.toggle('collapsed');
			header.classList.toggle('collapsed');
		});
	}
})();
